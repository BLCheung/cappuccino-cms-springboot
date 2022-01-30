package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.*;
import com.blcheung.zblmissyouadmin.dto.cms.*;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.kit.UserKit;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.service.*;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import com.blcheung.zblmissyouadmin.vo.cms.GroupPermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BLCheung
 * @date 2021/12/21 9:48 下午
 */
@Service
@Slf4j
public class CmsAdminServiceImpl implements CmsAdminService {
    @Autowired
    private CmsGroupService           cmsGroupService;
    @Autowired
    private CmsPermissionService      cmsPermissionService;
    @Autowired
    private CmsGroupPermissionService cmsGroupPermissionService;
    @Autowired
    private CmsUserGroupService       cmsUserGroupService;
    @Autowired
    private CmsUserService            cmsUserService;
    @Autowired
    private CmsRootService            cmsRootService;
    @Autowired
    private CmsUserIdentityService    cmsUserIdentityService;

    @Override
    public Boolean checkUserIsAdmin(Long userId) {
        List<Long> userAllGroupIds = this.cmsUserGroupService.getUserAllGroupIds(userId);
        if (userAllGroupIds.isEmpty()) return false;

        List<Long> adminLevelGroupIds = this.cmsGroupService.getAdminLevelGroupsIds();
        return CommonUtil.isContainAnyIds(userAllGroupIds, adminLevelGroupIds);
    }

    @Transactional
    @Override
    public Boolean createGroup(NewGroupDTO dto) {
        this.cmsGroupService.validateGroupNameExist(dto.getName());
        CmsGroupDO cmsGroupDO = CmsGroupDO.builder()
                                          .name(dto.getName())
                                          .info(dto.getInfo())
                                          .level(GroupLevel.USER)   // 管理员只能添加用户等级的角色群组
                                          .build();

        boolean saveSuccess = this.cmsGroupService.createGroup(cmsGroupDO);
        if (!saveSuccess) throw new DatabaseActionException(10200);

        // 校验分配的权限
        if (!ObjectUtils.isEmpty(dto.getPermissionIds()) && !dto.getPermissionIds()
                                                                .isEmpty()) {
            this.cmsPermissionService.validatePermissionExist(dto.getPermissionIds());
            Boolean dispatchSuccess = this.cmsGroupPermissionService.dispatchGroupPermission(cmsGroupDO.getId(),
                                                                                             dto.getPermissionIds());
            if (!dispatchSuccess) throw new FailedException(10221);
        }

        return true;
    }

    @Override
    public Boolean updateGroup(Long groupId, UpdateGroupDTO dto) {
        CmsGroupDO cmsGroupDO = this.cmsGroupService.getById(groupId);
        if (ObjectUtils.isEmpty(cmsGroupDO)) throw new NotFoundException(10208);

        if (!StringUtils.equals(cmsGroupDO.getName(), dto.getName())) {
            this.cmsGroupService.validateGroupNameExist(dto.getName());
        }

        return this.cmsGroupService.lambdaUpdate()
                                   .eq(CmsGroupDO::getId, cmsGroupDO.getId())
                                   .set(!StringUtils.isEmpty(dto.getName()), CmsGroupDO::getName, dto.getName())
                                   .set(null != dto.getInfo(), CmsGroupDO::getInfo, dto.getInfo())
                                   .update();
    }

    @Transactional
    @Override
    public Boolean deleteGroup(Long groupId) {
        this.validateGroupCanModify(groupId);
        this.cmsGroupService.validateGroupIdExist(groupId);

        // 待删除的分组下是否存在用户
        List<Long> groupAllUserIds = this.cmsUserGroupService.getGroupAllUserIds(groupId);
        if (!groupAllUserIds.isEmpty()) throw new ForbiddenException(10210);

        return this.cmsGroupService.removeById(groupId);
    }

    @Override
    public List<GroupVO> getAllUserLevelGroups() {
        List<CmsGroupDO> userLevelGroups = this.cmsGroupService.getAllUserLevelGroups();
        return BeanKit.transformList(userLevelGroups, GroupVO.class);
    }

    @Override
    public List<PermissionVO> getAssignablePermissions() {
        // TODO: 目前为获取所有挂载的权限为可分配的权限，后期做多管理员分组情况时得增加level字段判断不同级别的分组可分配的权限
        List<CmsPermissionDO> cmsPermissions = this.cmsPermissionService.lambdaQuery()
                                                                        .eq(CmsPermissionDO::getMount, true)
                                                                        .list();
        return BeanKit.transformList(cmsPermissions, PermissionVO.class);
    }

    @Override
    public GroupPermissionVO getGroupAndPermissions(Long groupId) {
        CmsGroupDO cmsGroupDO = this.cmsGroupService.lambdaQuery()
                                                    .eq(!ObjectUtils.isEmpty(groupId), CmsGroupDO::getId, groupId)
                                                    .oneOpt()
                                                    .orElseThrow(() -> new NotFoundException(10208));
        List<CmsPermissionDO> groupPermissions = this.cmsPermissionService.getPermissionByGroupId(cmsGroupDO.getId());

        return BeanKit.transform(cmsGroupDO, new GroupPermissionVO(groupPermissions));
    }

    @Transactional
    @Override
    public Boolean dispatchPermissions(DispatchPermissionsDTO dto) {
        Long groupId = dto.getGroupId();
        this.validateGroupCanModify(groupId);

        // 分配的权限列表
        List<Long> dispatchPermissionIds = dto.getPermissionIds();
        // 分组下的权限列表
        List<Long> currentGroupPermissionIds = this.cmsPermissionService.getPermissionIdsByGroupId(groupId);

        Boolean isInclude = CommonUtil.isIncludeEqualIds(dispatchPermissionIds, currentGroupPermissionIds);
        if (isInclude) return true;

        List<Long> addIds = Collections.emptyList();
        List<Long> removeIds = Collections.emptyList();

        if (!dispatchPermissionIds.isEmpty()) {
            // 校验分配的权限是否存在
            this.cmsPermissionService.validatePermissionExist(dispatchPermissionIds);
            // 从权限分配列表内将该分组所没有的权限添加进该分组的权限列表下
            addIds = dispatchPermissionIds.stream()
                                          .filter(dId -> currentGroupPermissionIds.isEmpty() ||
                                                         !currentGroupPermissionIds.contains(dId))
                                          .collect(Collectors.toList());
        }

        if (!currentGroupPermissionIds.isEmpty()) {
            // 从该分组的权限列表下将没有被包含在权限分配列表里的权限给删除
            removeIds = currentGroupPermissionIds.stream()
                                                 .filter(cId -> dispatchPermissionIds.isEmpty() ||
                                                                !dispatchPermissionIds.contains(cId))
                                                 .collect(Collectors.toList());
        }

        return this.cmsGroupPermissionService.dispatchGroupPermission(groupId, addIds) &&
               this.cmsGroupPermissionService.removeGroupPermission(groupId, removeIds);
    }

    @Override
    public PagingVO<UserGroupVO> getUserPage(QueryUsersDTO dto) {
        Page<CmsUserDO> pageable = PagingKit.build(dto, CmsUserDO.class);

        if (dto.getGroupId() == null) {
            // 当前只能查询管理员以下级别的用户
            pageable = this.cmsUserService.getUserPageByGroupLevelGE(pageable, GroupLevel.USER);
        } else {
            CmsUserDO cmsUserDO = UserKit.getUser();
            Boolean isRoot = this.cmsRootService.checkUserIsRoot(cmsUserDO.getId());
            Long rootGroupId = this.cmsGroupService.getRootGroupId();
            if (rootGroupId.equals(dto.getGroupId()) && !isRoot) {
                throw new UnAuthorizedException(10222);
            }
            pageable = this.cmsUserService.getUserPageByGroupId(pageable, dto.getGroupId());
        }

        // 每个用户都组装上分组信息
        return this.assembleUserGroupVO(pageable);
    }

    @Transactional
    @Override
    public Boolean updateUserGroup(Long userId, UpdateUserGroupDTO dto) {
        List<Long> dispatchGroupIds = dto.getGroupIds();

        CmsUserDO cmsUserDO = this.cmsUserService.getUserByUserId(userId)
                                                 .orElseThrow(() -> new NotFoundException(10103));

        List<Long> currentUserGroupIds = this.cmsGroupService.getUserGroupIds(cmsUserDO.getId());

        Boolean isInclude = CommonUtil.isIncludeEqualIds(dispatchGroupIds, currentUserGroupIds);
        if (isInclude) return true;

        List<Long> addIds = Collections.emptyList();
        List<Long> removeIds = Collections.emptyList();

        if (!dispatchGroupIds.isEmpty()) {
            List<Long> adminLevelGroups = this.cmsGroupService.getAdminLevelGroupsIds();
            Boolean isContain = CommonUtil.isContainAnyIds(dispatchGroupIds, adminLevelGroups);
            if (isContain) throw new ForbiddenException(10203);

            Boolean isAllExist = this.cmsGroupService.validateGroupIdExistBatch(dispatchGroupIds);
            if (!isAllExist) throw new NotFoundException(10202);

            addIds = dispatchGroupIds.stream()
                                     .filter(dId -> currentUserGroupIds.isEmpty() || !currentUserGroupIds.contains(dId))
                                     .collect(Collectors.toList());
        }

        if (!currentUserGroupIds.isEmpty()) {
            removeIds = currentUserGroupIds.stream()
                                           .filter(ugId -> dispatchGroupIds.isEmpty() ||
                                                           !dispatchGroupIds.contains(ugId))
                                           .collect(Collectors.toList());
        }

        return this.cmsUserGroupService.addUserGroupRelations(cmsUserDO.getId(), addIds) &&
               this.cmsUserGroupService.removeUserGroupRelations(cmsUserDO.getId(), removeIds);
    }

    @Transactional
    @Override
    public Boolean changeUserPassword(Long userId, ResetUserPasswordDTO dto) {
        CmsUserDO cmsUserDO = this.cmsUserService.getUserByUserId(userId)
                                                 .orElseThrow(() -> new NotFoundException(10103));
        // 当前是否超级管理员在访问
        CmsUserDO currentUser = UserKit.getUser();
        Boolean isRoot = this.cmsRootService.checkUserIsRoot(currentUser.getId());
        if (!isRoot) {
            // 目标修改的用户是否为管理员或以上级别
            Boolean isAdminOrRoot = this.checkUserIsAdmin(cmsUserDO.getId());
            if (isAdminOrRoot) throw new ForbiddenException();
        }

        return this.cmsUserIdentityService.changeUserPasswordIdentity(cmsUserDO.getId(), dto.getPassword());
    }

    @Transactional
    @Override
    public Boolean deleteUser(Long userId) {
        CmsUserDO cmsUserDO = this.cmsUserService.getUserByUserId(userId)
                                                 .orElseThrow(() -> new NotFoundException(10103));

        CmsUserDO currentUser = UserKit.getUser();
        Boolean isRoot = this.cmsRootService.checkUserIsRoot(currentUser.getId());
        if (!isRoot) {
            Boolean isAdminOrRootTarget = this.checkUserIsAdmin(cmsUserDO.getId());
            if (isAdminOrRootTarget) throw new ForbiddenException();
        }

        Boolean removeUserGroupSuccess = this.cmsUserGroupService.removeUserGroupByUserId(cmsUserDO.getId());
        if (!removeUserGroupSuccess) throw new DatabaseActionException(10100);

        Boolean removeUserIdentitySuccess = this.cmsUserIdentityService.removeUserIdentity(cmsUserDO.getId());
        if (!removeUserIdentitySuccess) throw new DatabaseActionException(10120);

        boolean removeUserSuccess = this.cmsUserService.removeById(cmsUserDO.getId());
        if (!removeUserSuccess) throw new DatabaseActionException(10100);

        return true;
    }

    /**
     * 拼装用户分页下的每个用户所属分组信息
     *
     * @param pageable
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO>
     * @author BLCheung
     * @date 2022/1/26 3:03 上午
     */
    @Override
    public PagingVO<UserGroupVO> assembleUserGroupVO(Page<CmsUserDO> pageable) {
        List<UserGroupVO> userGroupsVO = pageable.getRecords()
                                                 .stream()
                                                 .map(cmsUserDO -> {
                                                     // 查当前用户的分组信息
                                                     List<CmsGroupDO> userGroups = this.cmsGroupService.getUserGroups(
                                                             cmsUserDO.getId());
                                                     return BeanKit.transform(cmsUserDO, new UserGroupVO(userGroups));
                                                 })
                                                 .collect(Collectors.toList());
        return PagingKit.resolve(pageable, userGroupsVO);
    }


    /**
     * 校验该分组是否可以进行相关修改操作（管理员级别与游客分组不能进行修改）
     *
     * @param groupId
     * @author BLCheung
     * @date 2022/1/22 2:50 上午
     */
    private void validateGroupCanModify(Long groupId) {
        List<Long> adminGroupIds = this.cmsGroupService.getAdminLevelGroupsIds();
        if (adminGroupIds.contains(groupId)) throw new ForbiddenException(10211);

        Long guestGroupId = this.cmsGroupService.getGroupIdByLevel(GroupLevel.GUEST);
        if (guestGroupId.equals(groupId)) throw new ForbiddenException(10212);
    }
}
