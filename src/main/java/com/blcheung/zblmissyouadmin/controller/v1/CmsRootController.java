package com.blcheung.zblmissyouadmin.controller.v1;

import com.blcheung.zblmissyouadmin.common.annotations.permission.RootRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.dto.cms.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsRootService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.DeletedVO;
import com.blcheung.zblmissyouadmin.vo.PagingResultVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/26 9:52 下午
 */
@RestController
@RequestMapping("/cms/root")
@Validated
@RouterModule(name = "超级管理员")
@RootRequired
public class CmsRootController {
    @Autowired
    private CmsRootService cmsRootService;

    @GetMapping("/users/all")
    @RouterMeta(name = "查询所有用户与管理员", mount = false)
    public PagingResultVO<UserGroupVO> users(@Validated BasePagingDTO dto) {
        return this.cmsRootService.getAllUserByRoot(dto);
    }

    @GetMapping("/groups/all")
    @RouterMeta(name = "查询所有分组", mount = false)
    public List<GroupVO> groups() {
        return this.cmsRootService.getAllGroupByRoot();
    }

    @PostMapping("/admin/group")
    @RouterMeta(name = "创建管理员分组", mount = false)
    public CreatedVO createAdminGroup(@RequestBody @Validated NewAdminGroupDTO dto) {
        this.cmsRootService.createAdminGroup(dto);
        return ResultKit.resolveCreated();
    }

    @DeleteMapping("/admin/group/{id}")
    @RouterMeta(name = "删除管理员分组", mount = false)
    public DeletedVO deleteAdminGroup(@PathVariable @Positive Long id) {
        this.cmsRootService.deleteAdminGroup(id);
        return ResultKit.resolveDeleted();
    }
}
