package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.NewGroupDTO;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsGroupPermissionDO;
import com.blcheung.zblmissyouadmin.service.*;
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
    @Autowired
    private CmsUserGroupService       cmsUserGroupService;

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

    @Transactional
    @Override
    public boolean deleteGroup(Long groupId) {
        Long rootGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.ROOT);
        if (rootGroupId.equals(groupId)) throw new ForbiddenException(10204);

        Long adminGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.ADMIN);
        if (adminGroupId.equals(groupId)) throw new ForbiddenException(10205);

        Long guestGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.GUEST);
        if (guestGroupId.equals(groupId)) throw new ForbiddenException(10206);

        boolean isGroupExist = this.cmsGroupService.checkGroupExistById(groupId);
        if (!isGroupExist) throw new NotFoundException(10208);

        // 待删除的分组下是否存在用户
        List<Long> groupAllUserIds = this.cmsUserGroupService.getGroupAllUserIds(groupId);
        if (!groupAllUserIds.isEmpty()) throw new ForbiddenException(10210);

        return this.cmsGroupService.removeById(groupId);
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
