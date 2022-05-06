package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.model.CmsPermissionDO;
import com.blcheung.cappuccino.vo.cms.PermissionModuleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsPermissionService extends IService<CmsPermissionDO> {

    /**
     * 权限是否存在
     *
     * @param id
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/25 11:21 下午
     */
    Boolean checkPermissionExist(Long id);

    /**
     * 批量检查权限是否存在
     *
     * @param ids
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/25 11:30 下午
     */
    Boolean checkPermissionExistBatch(List<Long> ids);

    /**
     * 校验权限集合
     *
     * @param permissionIds
     * @author BLCheung
     * @date 2021/12/26 10:08 下午
     */
    void validatePermissionExist(List<Long> permissionIds);

    /**
     * 根据分组id集合获取所有权限
     *
     * @param groupIds
     * @return java.util.List<com.blcheung.cappuccino.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/11 9:23 下午
     */
    List<CmsPermissionDO> getPermissionsByGroupIds(List<Long> groupIds);

    /**
     * 根据分组id获取指定分组的所有权限
     *
     * @param groupId
     * @return java.util.List<com.blcheung.cappuccino.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/22 12:24 上午
     */
    List<CmsPermissionDO> getPermissionByGroupId(Long groupId);

    /**
     * 根据分组id获取指定分组的所有权限id集合
     *
     * @param groupId
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/22 8:22 下午
     */
    List<Long> getPermissionIdsByGroupId(Long groupId);

    /**
     * 组装权限模块集合
     *
     * @param permissions
     * @return java.util.List<com.blcheung.cappuccino.vo.cms.PermissionModuleVO>
     * @author BLCheung
     * @date 2022/4/18 6:27 上午
     */
    List<PermissionModuleVO> assemblePermissionModules(List<CmsPermissionDO> permissions);
}
