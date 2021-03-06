package com.blcheung.cappuccino.module.file.common;

import com.blcheung.cappuccino.common.exceptions.*;
import com.blcheung.cappuccino.module.file.bean.FileEntity;
import com.blcheung.cappuccino.module.file.configuration.CmsFileProperties;
import com.blcheung.cappuccino.module.file.kit.FileKit;
import com.blcheung.cappuccino.module.file.listener.OnUploadFileListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author BLCheung
 * @date 2022/1/16 2:59 上午
 */
@Slf4j
public abstract class AbstractUploader implements Uploader {

    /**
     * 外部实现的文件上传监听器回调
     */
    private OnUploadFileListener uploadFileListener;

    @Override
    public List<FileEntity> upload(MultiValueMap<String, MultipartFile> fileMultiValueMap,
                                   OnUploadFileListener listener) {
        this.uploadFileListener = listener;
        return this.upload(fileMultiValueMap);
    }

    @Override
    public List<FileEntity> upload(MultiValueMap<String, MultipartFile> fileMultiValueMap) {
        this.validateFiles(fileMultiValueMap);

        List<MultipartFile> allFiles = this.getAllFiles(fileMultiValueMap);

        this.validateFilesExt(allFiles);
        // 全局异常已经处理文件大小限制
        // this.validateFilesSize(allFiles);

        List<FileEntity> fileEntityList = new ArrayList<>();
        allFiles.forEach(file -> this.handleMultipartFile(fileEntityList, file));

        return fileEntityList;
    }

    /**
     * 具体的处理文件上传逻辑
     *
     * @param bytes    文件的字节数
     * @param filePath 文件的存放路径
     * @param fileName 文件名称
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/16 3:38 上午
     */
    protected abstract Boolean onUploadFile(byte[] bytes, String filePath, String fileName);

    /**
     * 获取指定上传文件的配置
     *
     * @return com.blcheung.cappuccino.module.file.configuration.CmsFileProperties
     * @author BLCheung
     * @date 2022/1/16 3:38 上午
     */
    protected abstract CmsFileProperties getFileProperties();

    /**
     * 获取上传文件后的访问url
     *
     * @param filePath 文件的目录路径
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/1/17 2:43 上午
     */
    protected abstract String getFileUrl(String filePath);

    /**
     * 获取文件的上传类型
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/1/18 6:26 上午
     */
    protected abstract String getFileType();

    /**
     * 获取文件存放路径
     *
     * @param newFileName
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/1/17 3:10 上午
     */
    protected abstract String getStorePath(String newFileName);

    /**
     * 获取文件名
     *
     * @param ext
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/1/17 2:47 上午
     */
    protected String getNewFileName(String ext) {
        return UUID.randomUUID()
                   .toString()
                   .replace("-", "") + ext;
    }

    /**
     * 获取文件字节码
     *
     * @param file
     * @return byte[]
     * @author BLCheung
     * @date 2022/1/17 3:17 上午
     */
    protected byte[] getFileBytes(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new FailedException(10020, "获取文件字节码出现错误");
        }

        return bytes;
    }


    private void handleMultipartFile(List<FileEntity> fileEntities, MultipartFile file) {
        if (file.isEmpty()) return;

        byte[] fileBytes = this.getFileBytes(file);
        String originFileName = file.getOriginalFilename();
        String ext = FileKit.getFileExt(originFileName);
        String newFileName = this.getNewFileName(ext);
        String storePath = this.getStorePath(newFileName);
        String fileUrl = this.getFileUrl(storePath);
        String md5 = FileKit.getFileMD5(fileBytes);
        Integer size = fileBytes.length;
        String fileType = this.getFileType();

        FileEntity fileEntity = FileEntity.builder()
                                          .name(newFileName)
                                          .originalName(originFileName)
                                          .extension(ext)
                                          .size(size)
                                          .path(storePath)
                                          .url(fileUrl)
                                          .md5(md5)
                                          .type(fileType)
                                          .build();
        // 有上传监听器，并且每个文件上传的前置回调都返回true则处理，否则不做处理
        if (this.uploadFileListener != null && !this.uploadFileListener.onEachFilePreUpload(fileEntity)) return;

        // 对应的上传逻辑是否成功
        Boolean isUploaded = this.onUploadFile(fileBytes, storePath, newFileName);
        if (isUploaded == null) {
            log.error("请重写onUploadFile()处理具体的上传逻辑!");
            throw new FailedException("后端未处理具体的文件上传逻辑!");
        }

        if (isUploaded) {
            fileEntities.add(fileEntity);
            if (this.uploadFileListener != null) this.uploadFileListener.onEachFileUploadAfter(fileEntity);
        }
    }

    private void validateFiles(MultiValueMap<String, MultipartFile> fileMap) {
        if (fileMap.isEmpty()) throw new NotFoundException(10021);

        int fileCount = this.getFileProperties()
                            .getCount();
        // 文件数量限制判断
        AtomicInteger fileMapSize = new AtomicInteger();
        for (String key : fileMap.keySet()) {
            fileMap.get(key)
                   .stream()
                   .filter(file -> !file.isEmpty())
                   .forEach(file -> fileMapSize.getAndIncrement());
        }

        if (fileMapSize.get() > fileCount) {
            throw new FileTooManyException(10022);
        }
    }

    private void validateFilesSize(List<MultipartFile> fileList) {
        long fileMaxSizeLimit = this.getSingleFileMaxSizeLimit();
        fileList.stream()
                .filter(file -> file.getSize() > fileMaxSizeLimit)
                .findAny()
                .ifPresent(file -> { throw new FileTooLargeException(); });
    }

    private void validateFilesExt(List<MultipartFile> fileList) {
        String[] extWhiteList = this.getExtWhiteList();
        if (extWhiteList.length == 0) return;

        // 所有文件的后缀类型集合
        List<String> unSupportExt = fileList.stream()
                                            .map(file -> FileKit.getFileExt(file.getOriginalFilename()))
                                            .filter(fileExt -> !this.isFileExtValid(extWhiteList, fileExt))
                                            .collect(Collectors.toList());

        if (!unSupportExt.isEmpty())
            throw new FileExtensionException("不支持" + StringUtils.join(unSupportExt, ",") + "类型的文件");
    }

    private boolean isFileExtValid(String[] extList, String fileExt) {
        for (String ext : extList) {
            if (StringUtils.equalsIgnoreCase(ext, fileExt)) {
                return true;
            }
        }
        return false;
    }

    private String[] getExtWhiteList() {
        String[] whitelist = this.getFileProperties()
                                 .getWhitelist();
        return ObjectUtils.isEmpty(whitelist) ? new String[ 0 ] : whitelist;
    }

    private Long getSingleFileMaxSizeLimit() {
        String maxLimit = this.getFileProperties()
                              .getMaxFileSize();
        return FileKit.parseSize(maxLimit);
    }

    private Long getTotalFileMaxSizeLimit() {
        String maxLimit = this.getFileProperties()
                              .getMaxRequestSize();
        return FileKit.parseSize(maxLimit);
    }

    private List<MultipartFile> getAllFiles(MultiValueMap<String, MultipartFile> fileMap) {
        return fileMap.keySet()
                      .stream()
                      .flatMap(key -> fileMap.get(key)
                                             .stream())
                      .collect(Collectors.toList());
    }
}
