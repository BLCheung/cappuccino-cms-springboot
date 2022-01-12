package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsGroupService extends IService<CmsGroupDO> {

    /**
     * 创建分组
     *
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:37 上午
     */
    boolean createGroup(CmsGroupDO groupDO);

    /**
     * 通过id检查分组是否存在
     *
     * @param id
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:33 上午
     */
    boolean checkGroupExistById(Long id);

    /**
     * 通过分组id集合批量检查分组是否存在
     *
     * @param ids
     * @return boolean
     * @author BLCheung
     * @date 2021/12/29 11:24 下午
     */
    boolean checkGroupExistByIds(List<Long> ids);

    /**
     * 通过名字检查分组是否存在
     *
     * @param name
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:33 上午
     */
    boolean checkGroupExistByName(String name);

    /**
     * 通过分组级别枚举获取分组id
     *
     * @param groupLevel
     * @return java.lang.Long
     * @author BLCheung
     * @date 2021/12/21 12:53 上午
     */
    Long getGroupIdByEnum(GroupLevel groupLevel);

    /**
     * 校验分组
     *
     * @param groupName
     * @author BLCheung
     * @date 2021/12/26 10:10 下午
     */
    void validateGroupNameExist(String groupName);

    /**
     * 获取用户所有所属分组的id
     *
     * @param userId
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/11 9:14 下午
     */
    List<Long> getUserGroupIds(Long userId);

    /**
     * 获取所有管理员级别及以上的分组id
     *
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/11 10:02 下午
     */
    List<Long> getAdminLevelGroups();

    /**
     * 获取超级管理员分组的id
     *
     * @return java.lang.Long
     * @author BLCheung
     * @date 2022/1/11 10:25 下午
     */
    Long getRootGroupId();
}
