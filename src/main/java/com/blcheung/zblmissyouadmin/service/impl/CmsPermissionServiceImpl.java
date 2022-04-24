package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.mapper.CmsPermissionMapper;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.service.CmsPermissionService;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionModuleVO;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
                   .select(CmsPermissionDO::getId)
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
        if (ObjectUtils.isEmpty(permissionIds)) return;

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

    @Override
    public List<PermissionModuleVO> assemblePermissionModules(List<CmsPermissionDO> permissions) {
        List<PermissionModuleVO> modules = new ArrayList<>();
        for (CmsPermissionDO permission : permissions) {
            int moduleIndex = this.getModuleIndex(modules, permission.getModule());
            if (moduleIndex == -1) {
                List<PermissionVO> permissionList = new ArrayList<>();
                permissionList.add(BeanKit.transform(permission, new PermissionVO()));
                modules.add(new PermissionModuleVO(permission.getModule(), permissionList));
            } else {
                PermissionModuleVO moduleVO = modules.get(moduleIndex);
                // TODO: 权限增加index索引并根据索引在当前权限模块内排序权限
                moduleVO.getPermissions()
                        .add(BeanKit.transform(permission, new PermissionVO()));
            }
        }
        return modules;
    }


    private int getModuleIndex(List<PermissionModuleVO> modules, String module) {
        if (modules.isEmpty()) return -1;
        for (int i = 0; i < modules.size(); i++) {
            PermissionModuleVO moduleVO = modules.get(i);
            if (module.equals(moduleVO.getModule())) return i;
        }
        return -1;
    }
}
