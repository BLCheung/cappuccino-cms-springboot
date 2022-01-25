package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.cms.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.blcheung.zblmissyouadmin.service.*;
import com.blcheung.zblmissyouadmin.vo.PagingResultVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PagingResultVO<UserGroupVO> getAllUserByRoot(BasePagingDTO dto) {
        Page<CmsUserDO> userAdminPageable = PagingKit.build(dto, CmsUserDO.class);
        userAdminPageable = this.cmsUserService.getUserAdminPageByRoot(userAdminPageable);

        return this.cmsAdminService.assembleUserGroupVO(userAdminPageable);
    }

    @Override
    public List<GroupVO> getAllGroupByRoot() {
        List<CmsGroupDO> adminGroups = this.cmsGroupService.getGroupsByLevelGE(GroupLevel.ADMIN);
        return BeanKit.transformList(adminGroups, GroupVO.class);
    }
}
