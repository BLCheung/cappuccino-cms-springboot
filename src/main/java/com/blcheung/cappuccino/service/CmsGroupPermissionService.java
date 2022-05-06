package com.blcheung.cappuccino.service;

import com.blcheung.cappuccino.model.CmsGroupPermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsGroupPermissionService extends IService<CmsGroupPermissionDO> {

    /**
     * 批量增加分组与权限的关系数据
     *
     * @param groupId
     * @param addPermissionIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/22 5:04 上午
     */
    Boolean dispatchGroupPermission(Long groupId, List<Long> addPermissionIds);

    /**
     * 批量删除分组与权限的关系数据
     *
     * @param groupId
     * @param removePermissionIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/22 5:15 上午
     */
    Boolean removeGroupPermission(Long groupId, List<Long> removePermissionIds);
}
