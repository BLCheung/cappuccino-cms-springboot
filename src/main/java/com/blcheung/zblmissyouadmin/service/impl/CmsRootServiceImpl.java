package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.cms.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.dto.cms.QueryUsersDTO;
import com.blcheung.zblmissyouadmin.dto.cms.UpdateUserGroupDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.blcheung.zblmissyouadmin.service.*;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import com.blcheung.zblmissyouadmin.vo.PagingVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BLCheung
 * @date 2021/12/26 10:04 下午
 */
@Service
public class CmsRootServiceImpl implements CmsRootService {

    @Autowired
    private CmsUserService      cmsUserService;
    @Autowired
    private CmsGroupService     cmsGroupService;
    @Autowired
    private CmsUserGroupService cmsUserGroupService;
    @Autowired
    private CmsAdminService     cmsAdminService;

    @Override
    public Boolean checkUserIsRoot(Long userId) {
        Long rootGroupId = this.cmsGroupService.getRootGroupId();
        CmsUserGroupDO userGroupRelation = this.cmsUserGroupService.getUserGroupRelation(userId, rootGroupId);
        return userGroupRelation != null;
    }

    @Transactional
    @Override
    public Boolean createAdminGroup(NewAdminGroupDTO dto) {
        this.cmsGroupService.validateGroupNameExist(dto.getName());

        CmsGroupDO cmsGroupDO = CmsGroupDO.builder()
                                          .name(dto.getName())
                                          .info(dto.getInfo())
                                          .level(GroupLevel.ADMIN)  // 超级管理员添加管理员分组级别
                                          .build();

        return this.cmsGroupService.createGroup(cmsGroupDO);
    }

    @Transactional
    @Override
    public Boolean deleteAdminGroup(Long adminGroupId) {
        Long rootGroupId = this.cmsGroupService.getGroupIdByLevel(GroupLevel.ROOT);
        if (rootGroupId.equals(adminGroupId)) throw new ForbiddenException(10204);

        boolean isAdminGroupExist = this.cmsGroupService.checkGroupExistById(adminGroupId);
        if (!isAdminGroupExist) throw new NotFoundException(10208);

        // 该管理员分组下是否还存在管理员
        List<Long> adminGroupAllUserIds = this.cmsUserGroupService.getGroupAllUserIds(adminGroupId);
        if (!adminGroupAllUserIds.isEmpty()) throw new ForbiddenException(10210);

        return this.cmsGroupService.removeById(adminGroupId);
    }

    @Override
    public PagingVO<UserGroupVO> getAllUserByRoot(QueryUsersDTO dto) {
        Page<CmsUserDO> userAdminPageable = PagingKit.build(dto, CmsUserDO.class);

        if (dto.getGroupId() == null) {
            userAdminPageable = this.cmsUserService.getUserAdminPageByRoot(userAdminPageable);
        } else {
            userAdminPageable = this.cmsUserService.getUserPageByGroupId(userAdminPageable, dto.getGroupId());
        }

        return this.cmsAdminService.assembleUserGroupVO(userAdminPageable);
    }

    @Override
    public List<GroupVO> getAllGroupByRoot() {
        // 获取管理员以及以下级别分组
        List<CmsGroupDO> adminGroups = this.cmsGroupService.getGroupsByLevelGE(GroupLevel.ADMIN);
        return BeanKit.transformList(adminGroups, GroupVO.class);
    }

    @Transactional
    @Override
    public Boolean updateUserGroupByRoot(Long userId, UpdateUserGroupDTO dto) {
        // 被分配的分组id集合
        List<Long> dispatchGroupIds = dto.getGroupIds();
        this.validateGroupCanModify(dispatchGroupIds);

        CmsUserDO cmsUserDO = this.cmsUserService.getUserByUserId(userId)
                                                 .orElseThrow(() -> new NotFoundException(10103));

        // 用户当前所有所属分组id
        List<Long> userCurrentGroupIds = this.cmsGroupService.getUserGroupIds(cmsUserDO.getId());
        Boolean isInclude = CommonUtil.isIncludeEqualIds(dispatchGroupIds, userCurrentGroupIds);
        if (isInclude) return true;

        List<Long> addIds = Collections.emptyList();
        List<Long> removeIds = Collections.emptyList();

        if (!dispatchGroupIds.isEmpty()) {
            Boolean isAllExist = this.cmsGroupService.validateGroupIdExistBatch(dispatchGroupIds);
            if (!isAllExist) throw new NotFoundException(10202);

            addIds = dispatchGroupIds.stream()
                                     .filter(dId -> userCurrentGroupIds.isEmpty() || !userCurrentGroupIds.contains(dId))
                                     .collect(Collectors.toList());
        }

        if (!userCurrentGroupIds.isEmpty()) {
            removeIds = userCurrentGroupIds.stream()
                                           .filter(ugId -> dispatchGroupIds.isEmpty() ||
                                                           !dispatchGroupIds.contains(ugId))
                                           .collect(Collectors.toList());
        }

        return this.cmsUserGroupService.addUserGroupRelations(cmsUserDO.getId(), addIds) &&
               this.cmsUserGroupService.removeUserGroupRelations(cmsUserDO.getId(), removeIds);
    }


    private void validateGroupCanModify(List<Long> groupIds) {
        if (groupIds.isEmpty()) return;
        Long rootGroupId = this.cmsGroupService.getRootGroupId();
        if (CommonUtil.isContainOneId(rootGroupId, groupIds)) throw new ForbiddenException(10213);
    }
}
