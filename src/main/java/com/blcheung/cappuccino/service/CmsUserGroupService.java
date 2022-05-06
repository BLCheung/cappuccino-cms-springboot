package com.blcheung.cappuccino.service;

import com.blcheung.cappuccino.model.CmsUserGroupDO;
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

    /**
     * 获取用户的所有分组id
     *
     * @param userId
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/11 10:43 下午
     */
    List<Long> getUserAllGroupIds(Long userId);

    /**
     * 获取指定的用户与分组的关联实体
     *
     * @param userId
     * @param groupId
     * @return com.blcheung.cappuccino.model.CmsUserGroupDO
     * @author BLCheung
     * @date 2022/1/11 10:31 下午
     */
    CmsUserGroupDO getUserGroupRelation(Long userId, Long groupId);

    /**
     * 增加用户分组关系
     *
     * @param userId
     * @param addGroupIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/25 8:23 下午
     */
    Boolean addUserGroupRelations(Long userId, List<Long> addGroupIds);

    /**
     * 通过用户id移除用户分组关系
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 9:55 下午
     */
    Boolean removeUserGroupByUserId(Long userId);

    /**
     * 移除用户分组关系
     *
     * @param userId
     * @param removeGroupIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/25 8:23 下午
     */
    Boolean removeUserGroupRelations(Long userId, List<Long> removeGroupIds);
}
