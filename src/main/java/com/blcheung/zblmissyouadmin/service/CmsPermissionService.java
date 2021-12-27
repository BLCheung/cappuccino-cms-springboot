package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
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
}
