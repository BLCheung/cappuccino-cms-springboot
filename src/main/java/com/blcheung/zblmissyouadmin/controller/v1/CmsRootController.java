package com.blcheung.zblmissyouadmin.controller.v1;

import com.blcheung.zblmissyouadmin.dto.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsRootService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.DeletedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2021/12/26 9:52 下午
 */
@RestController
@RequestMapping("/cms/root")
@Validated
public class CmsRootController {
    @Autowired
    private CmsRootService cmsRootService;

    @PostMapping("/admin/group")
    public CreatedVO createAdminGroup(@RequestBody @Validated NewAdminGroupDTO dto) {
        this.cmsRootService.createAdminGroup(dto);
        return ResultKit.resolveCreated();
    }

    @DeleteMapping("/admin/group/{id}")
    public DeletedVO deleteAdminGroup(@PathVariable @Positive Long id) {
        this.cmsRootService.deleteAdminGroup(id);
        return ResultKit.resolveDeleted();
    }
}
