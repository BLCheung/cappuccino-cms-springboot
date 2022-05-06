package com.blcheung.cappuccino.controller.v1.cms;


import com.blcheung.cappuccino.common.annotations.permission.LoginRequired;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.CmsFileService;
import com.blcheung.cappuccino.vo.cms.FileVO;
import com.blcheung.cappuccino.vo.common.ResultVO;
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
@RequestMapping("/file")
@LoginRequired
public class CmsFileController {

    @Autowired
    private CmsFileService cmsFileService;

    /**
     * @param request 携带文件的request
     * @return com.blcheung.cappuccino.vo.common.ResultVO<java.util.List < com.blcheung.cappuccino.vo.cms.FileVO>>
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
