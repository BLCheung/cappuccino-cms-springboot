package com.blcheung.zblmissyouadmin.controller.v1;

import com.blcheung.zblmissyouadmin.common.annotations.permission.AdminRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.QueryUsersDTO;
import com.blcheung.zblmissyouadmin.dto.cms.DispatchPermissionsDTO;
import com.blcheung.zblmissyouadmin.dto.cms.NewGroupDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.CmsAdminService;
import com.blcheung.zblmissyouadmin.vo.*;
import com.blcheung.zblmissyouadmin.vo.cms.GroupPermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/22 9:08 下午
 */
@RestController
@RequestMapping("/cms/admin")
@Validated
@RouterModule(name = "管理员")
@AdminRequired
public class CmsAdminController {

    @Autowired
    private CmsAdminService cmsAdminService;

    @GetMapping("/users")
    @RouterMeta(name = "查询用户", mount = false)
    public PagingResultVO<UserVO> users(@Validated QueryUsersDTO dto) {
        return this.cmsAdminService.getUserPage(dto);
    }

    @GetMapping("/group/{id}")
    @RouterMeta(name = "查询一个分组及其权限", mount = false)
    public ResultVO<GroupPermissionVO> group(@PathVariable @Positive Long id) {
        GroupPermissionVO groupPermissionVO = this.cmsAdminService.getGroupAndPermissions(id);
        return ResultKit.resolve(groupPermissionVO);
    }

    @GetMapping("/group/all")
    @RouterMeta(name = "查询所有分组", mount = false)
    public ResultVO<List<GroupVO>> groups() {
        List<GroupVO> userLevelGroups = this.cmsAdminService.getAllUserLevelGroups();
        return ResultKit.resolve(userLevelGroups);
    }

    @PostMapping("/group")
    @RouterMeta(name = "创建分组", mount = false)
    public CreatedVO createGroup(@RequestBody @Validated NewGroupDTO dto) {
        this.cmsAdminService.createGroup(dto);
        return ResultKit.resolveCreated();
    }

    @DeleteMapping("/group/{id}")
    @RouterMeta(name = "删除分组", mount = false)
    public DeletedVO deleteGroup(@PathVariable @Positive Long id) {
        this.cmsAdminService.deleteGroup(id);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/permissions")
    @RouterMeta(name = "查询所有可分配权限", mount = false)
    public ResultVO<List<PermissionVO>> permissions() {
        List<PermissionVO> assignablePermissions = this.cmsAdminService.getAssignablePermissions();
        return ResultKit.resolve(assignablePermissions);
    }

    @PostMapping("/permissions/dispatch")
    @RouterMeta(name = "分组分配权限", mount = false)
    public UpdatedVO dispatchPermissions(@RequestBody @Validated DispatchPermissionsDTO dto) {
        Boolean dispatchSuccess = this.cmsAdminService.dispatchPermissions(dto);
        if (!dispatchSuccess) throw new FailedException(10221);
        return ResultKit.resolveUpdated();
    }
}
