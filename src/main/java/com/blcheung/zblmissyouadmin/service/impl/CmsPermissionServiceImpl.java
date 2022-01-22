package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.mapper.CmsPermissionMapper;
import com.blcheung.zblmissyouadmin.service.CmsPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsPermissionServiceImpl extends ServiceImpl<CmsPermissionMapper, CmsPermissionDO>
        implements CmsPermissionService {

    @Override
    public Boolean checkPermissionExist(Long id) {
        return this.lambdaQuery()
                   .eq(CmsPermissionDO::getId, id)
                   .exists();
    }

    @Override
    public Boolean checkPermissionExistBatch(List<Long> ids) {
        return this.lambdaQuery()
                   .select(CmsPermissionDO::getId)
                   .in(CmsPermissionDO::getId, ids)
                   .exists();
    }

    @Override
    public void validatePermissionExist(List<Long> permissionIds) {
        Boolean allPermissionExist = this.checkPermissionExistBatch(permissionIds);
        if (!allPermissionExist) throw new ForbiddenException(10207);
    }

    @Override
    public List<CmsPermissionDO> getPermissionsByGroupIds(List<Long> groupIds) {
        List<CmsPermissionDO> permissions = this.getBaseMapper()
                                                .getPermissionsByGroupIds(groupIds);
        return permissions.isEmpty() ? Collections.emptyList() : permissions;
    }

    @Override
    public List<CmsPermissionDO> getPermissionByGroupId(Long groupId) {
        List<CmsPermissionDO> permissions = this.getBaseMapper()
                                                .getPermissionByGroupId(groupId);
        return permissions.isEmpty() ? Collections.emptyList() : permissions;
    }

    @Override
    public List<Long> getPermissionIdsByGroupId(Long groupId) {
        List<Long> permissionIds = this.getBaseMapper()
                                       .getPermissionIdsByGroupId(groupId);
        return permissionIds.isEmpty() ? Collections.emptyList() : permissionIds;
    }

}
