package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.model.CmsGroupPermissionDO;
import com.blcheung.zblmissyouadmin.mapper.CmsGroupPermissionMapper;
import com.blcheung.zblmissyouadmin.service.CmsGroupPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsGroupPermissionServiceImpl extends ServiceImpl<CmsGroupPermissionMapper, CmsGroupPermissionDO>
        implements CmsGroupPermissionService {

    @Override
    public Boolean dispatchGroupPermission(Long groupId, List<Long> addPermissionIds) {
        if (addPermissionIds.isEmpty()) return true;

        List<CmsGroupPermissionDO> relations = addPermissionIds.stream()
                                                               .map(permissionId -> new CmsGroupPermissionDO(groupId,
                                                                                                             permissionId))
                                                               .collect(Collectors.toList());
        return this.getBaseMapper()
                   .saveBatch(relations) > 0;
    }

    @Override
    public Boolean removeGroupPermission(Long groupId, List<Long> removePermissionIds) {
        if (removePermissionIds.isEmpty()) return true;
        return this.getBaseMapper()
                   .deleteBatch(groupId, removePermissionIds) > 0;
    }
}
