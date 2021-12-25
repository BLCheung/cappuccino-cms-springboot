package com.blcheung.zblmissyouadmin.controller.v1;

import com.blcheung.zblmissyouadmin.dto.NewGroupDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsAdminService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.DeletedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

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

    @PostMapping("/group")
    public CreatedVO createGroup(@RequestBody @Validated NewGroupDTO dto) {
        this.cmsAdminService.createGroup(dto);
        return ResultKit.resolveCreated();
    }

    @DeleteMapping("/group/{id}")
    public DeletedVO deleteGroup(@PathVariable @Positive Long id) {
        this.cmsAdminService.deleteGroup(id);
        return ResultKit.resolveDeleted();
    }
}
