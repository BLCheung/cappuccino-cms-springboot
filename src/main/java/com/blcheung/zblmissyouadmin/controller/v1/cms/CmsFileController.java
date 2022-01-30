package com.blcheung.zblmissyouadmin.controller.v1.cms;


import com.blcheung.zblmissyouadmin.common.annotations.permission.LoginRequired;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsFileService;
import com.blcheung.zblmissyouadmin.vo.ResultVO;
import com.blcheung.zblmissyouadmin.vo.cms.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * <p>
 * 文件相关控制器
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/cms/file")
@LoginRequired
public class CmsFileController {

    @Autowired
    private CmsFileService cmsFileService;

    /**
     * @param request 携带文件的request
     * @return com.blcheung.zblmissyouadmin.vo.ResultVO<java.util.List < com.blcheung.zblmissyouadmin.vo.cms.FileVO>>
     * @author BLCheung
     * @date 2022/1/18 5:00 上午
     */
    @PostMapping
    public ResultVO<List<FileVO>> uploadFile(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> fileMaps = request.getMultiFileMap();
        List<FileVO> fileVOs = this.cmsFileService.upload(fileMaps);

        return ResultKit.resolve(fileVOs);
    }
}
