package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.mapper.CmsFileMapper;
import com.blcheung.cappuccino.model.CmsFileDO;
import com.blcheung.cappuccino.module.file.bean.FileEntity;
import com.blcheung.cappuccino.module.file.common.Uploader;
import com.blcheung.cappuccino.module.file.listener.OnUploadFileListener;
import com.blcheung.cappuccino.service.CmsFileService;
import com.blcheung.cappuccino.vo.cms.FileVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsFileServiceImpl extends ServiceImpl<CmsFileMapper, CmsFileDO> implements CmsFileService {

    @Autowired
    private Uploader uploader;

    @Override
    public List<FileVO> upload(MultiValueMap<String, MultipartFile> filesMap) {
        List<FileVO> fileVOs = new ArrayList<>();

        this.uploader.upload(filesMap, new OnUploadFileListener() {
            @Override
            public Boolean onEachFilePreUpload(FileEntity file) {
                CmsFileDO cmsFileDO = CmsFileServiceImpl.this.getFileByMD5(file.getType(), file.getMd5());
                // 不存在，则在上传完毕的后置回调里保存文件数据
                if (cmsFileDO == null) return true;

                // 已存在，则直接转化，并返回
                CmsFileServiceImpl.this.assembleFile(fileVOs, cmsFileDO);
                return false;
            }

            @Override
            public void onEachFileUploadAfter(FileEntity file) {
                // 构建并插入数据库
                CmsFileDO cmsFileDO = new CmsFileDO();
                BeanUtils.copyProperties(file, cmsFileDO);
                CmsFileServiceImpl.this.getBaseMapper()
                                       .insert(cmsFileDO);

                // 转化，并返回
                CmsFileServiceImpl.this.assembleFile(fileVOs, cmsFileDO);
            }
        });

        return fileVOs;
    }

    @Override
    public CmsFileDO getFileByMD5(String type, String md5) {
        return this.lambdaQuery()
                   .eq(CmsFileDO::getType, type)
                   .eq(CmsFileDO::getMd5, md5)
                   .one();
    }

    @Override
    public Boolean checkFileExist(String md5) {
        return this.lambdaQuery()
                   .eq(CmsFileDO::getMd5, md5)
                   .exists();
    }


    private void assembleFile(List<FileVO> fileVOs, CmsFileDO cmsFileDO) {
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(cmsFileDO, fileVO);
        fileVOs.add(fileVO);
    }
}
