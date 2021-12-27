package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
import com.blcheung.zblmissyouadmin.service.CmsRootService;
import com.blcheung.zblmissyouadmin.service.CmsUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/26 10:04 下午
 */
@Service
public class CmsRootServiceImpl implements CmsRootService {

    @Autowired
    private CmsGroupService     cmsGroupService;
    @Autowired
    private CmsUserGroupService cmsUserGroupService;

    @Transactional
    @Override
    public boolean createAdminGroup(NewAdminGroupDTO dto) {
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
    public boolean deleteAdminGroup(Long adminGroupId) {
        Long rootGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.ROOT);
        if (rootGroupId.equals(adminGroupId)) throw new ForbiddenException(10204);

        boolean isAdminGroupExist = this.cmsGroupService.checkGroupExistById(adminGroupId);
        if (!isAdminGroupExist) throw new NotFoundException(10208);

        // 该管理员分组下是否还存在管理员
        List<Long> adminGroupAllUserIds = this.cmsUserGroupService.getGroupAllUserIds(adminGroupId);
        if (!adminGroupAllUserIds.isEmpty()) throw new ForbiddenException(10210);

        return this.cmsGroupService.removeById(adminGroupId);
    }
}
