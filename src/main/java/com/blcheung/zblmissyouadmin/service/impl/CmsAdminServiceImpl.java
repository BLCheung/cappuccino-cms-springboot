package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.dto.NewGroupDTO;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsGroupPermissionDO;
import com.blcheung.zblmissyouadmin.service.CmsAdminService;
import com.blcheung.zblmissyouadmin.service.CmsGroupPermissionService;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
import com.blcheung.zblmissyouadmin.service.CmsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BLCheung
 * @date 2021/12/21 9:48 下午
 */
@Service
public class CmsAdminServiceImpl implements CmsAdminService {
    @Autowired
    private CmsGroupService           cmsGroupService;
    @Autowired
    private CmsPermissionService      cmsPermissionService;
    @Autowired
    private CmsGroupPermissionService cmsGroupPermissionService;

    @Transactional
    @Override
    public boolean createGroup(NewGroupDTO dto) {
        this.validateGroupNameExist(dto.getName());
        CmsGroupDO cmsGroupDO = CmsGroupDO.builder()
                                          .name(dto.getName())
                                          .info(dto.getInfo())
                                          .level(GroupLevel.USER)   // 管理员只能添加用户等级的角色群组
                                          .build();

        this.cmsGroupService.createGroup(cmsGroupDO);

        // 校验分配的权限
        if (!ObjectUtils.isEmpty(dto.getPermissionIds()) && !dto.getPermissionIds()
                                                                .isEmpty()) {
            this.validatePermissionExist(dto.getPermissionIds());
            List<CmsGroupPermissionDO> groupPermissionRelations = dto.getPermissionIds()
                                                                     .stream()
                                                                     .map(permissionId -> new CmsGroupPermissionDO(
                                                                             cmsGroupDO.getId(), permissionId))
                                                                     .collect(Collectors.toList());
            this.cmsGroupPermissionService.saveBatch(groupPermissionRelations);
        }

        return true;
    }


    private void validateGroupNameExist(String groupName) {
        boolean exist = this.cmsGroupService.checkGroupExistByName(groupName);
        if (exist) throw new ForbiddenException(10201);
    }

    private void validatePermissionExist(List<Long> permissionIds) {
        Boolean allPermissionExist = this.cmsPermissionService.checkPermissionExistBatch(permissionIds);
        if (!allPermissionExist) throw new ForbiddenException(10207);
    }
}
