package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.model.CmsFileDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.vo.cms.FileVO;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsFileService extends IService<CmsFileDO> {

    /**
     * 进行上传文件
     *
     * @param fileMultiValueMap 文件数据
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.cms.FileVO>
     * @author BLCheung
     * @date 2022/1/18 2:33 上午
     */
    List<FileVO> upload(MultiValueMap<String, MultipartFile> fileMultiValueMap);

    /**
     * 通过md5获取文件
     *
     * @param md5
     * @return com.blcheung.zblmissyouadmin.model.CmsFileDO
     * @author BLCheung
     * @date 2022/1/18 4:01 上午
     */
    CmsFileDO getFileByMD5(String md5);

    /**
     * 检查文件是否已存在
     *
     * @param md5 文件的md5
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/18 2:34 上午
     */
    Boolean checkFileExist(String md5);
}
