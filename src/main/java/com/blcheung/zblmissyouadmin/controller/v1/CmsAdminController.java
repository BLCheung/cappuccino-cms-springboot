package com.blcheung.zblmissyouadmin.controller.v1;

import com.blcheung.zblmissyouadmin.dto.NewGroupDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsAdminService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BLCheung
 * @date 2021/12/22 9:08 下午
 */
@RestController
@RequestMapping("/cms/admin")
@Validated
public class CmsAdminController {

    @Autowired
    private CmsAdminService cmsAdminService;

    @PostMapping("/create_group")
    public CreatedVO createGroup(@RequestBody @Validated NewGroupDTO dto) {
        this.cmsAdminService.createGroup(dto);
        return ResultKit.resolveCreated();
    }
}
