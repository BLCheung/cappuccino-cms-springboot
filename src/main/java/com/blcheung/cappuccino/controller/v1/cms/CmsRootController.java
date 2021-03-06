package com.blcheung.cappuccino.controller.v1.cms;

import com.blcheung.cappuccino.common.annotations.permission.RootRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.cms.NewAdminGroupDTO;
import com.blcheung.cappuccino.dto.cms.QueryUsersDTO;
import com.blcheung.cappuccino.dto.cms.UpdateUserGroupDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.CmsRootService;
import com.blcheung.cappuccino.vo.cms.GroupVO;
import com.blcheung.cappuccino.vo.cms.UserGroupVO;
import com.blcheung.cappuccino.vo.common.*;
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
@RequestMapping("/root")
@Validated
@RootRequired
@RouterModule(name = "超级管理员")
public class CmsRootController {
    @Autowired
    private CmsRootService cmsRootService;

    @GetMapping("/users/all")
    @RouterMeta(name = "查询所有用户与管理员", mount = false)
    public ResultVO<PagingVO<UserGroupVO>> users(@Validated QueryUsersDTO dto) {
        return ResultKit.resolve(this.cmsRootService.getAllUserByRoot(dto));
    }

    @GetMapping("/groups/all")
    @RouterMeta(name = "查询所有分组", mount = false)
    public ResultVO<List<GroupVO>> groups() {
        List<GroupVO> allGroups = this.cmsRootService.getAllGroupByRoot();
        return ResultKit.resolve(allGroups);
    }

    @PostMapping("/user/{id}/group")
    @RouterMeta(name = "更新用户分组", mount = false)
    public UpdatedVO updateUserGroup(@PathVariable @Positive Long id, @RequestBody @Validated UpdateUserGroupDTO dto) {
        Boolean updateSuccess = this.cmsRootService.updateUserGroupByRoot(id, dto);
        if (!updateSuccess) throw new FailedException(10104);
        return ResultKit.resolveUpdated();
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
