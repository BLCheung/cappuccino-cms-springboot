package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsUserGroupService extends IService<CmsUserGroupDO> {

    /**
     * 获取分组下所有用户的id
     *
     * @param groupId
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2021/12/26 3:52 上午
     */
    List<Long> getGroupAllUserIds(Long groupId);
}
