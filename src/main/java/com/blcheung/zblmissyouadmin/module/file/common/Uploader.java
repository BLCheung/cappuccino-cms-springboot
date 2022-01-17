package com.blcheung.zblmissyouadmin.module.file.common;

import com.blcheung.zblmissyouadmin.module.file.bean.FileEntity;
import com.blcheung.zblmissyouadmin.module.file.listener.OnUploadFileListener;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/16 3:01 上午
 */
public interface Uploader {

    /**
     * 上传文件
     *
     * @param fileMultiValueMap 文件数据
     * @return java.util.List<com.blcheung.zblmissyouadmin.module.file.bean.FileEntity>
     * @author BLCheung
     * @date 2022/1/16 3:05 上午
     */
    List<FileEntity> upload(MultiValueMap<String, MultipartFile> fileMultiValueMap);

    /**
     * 上传文件
     *
     * @param fileMultiValueMap 文件数据
     * @param listener          文件上传监听器
     * @return java.util.List<com.blcheung.zblmissyouadmin.module.file.bean.FileEntity>
     * @author BLCheung
     * @date 2022/1/16 3:05 上午
     */
    List<FileEntity> upload(MultiValueMap<String, MultipartFile> fileMultiValueMap, OnUploadFileListener listener);
}
