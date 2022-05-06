package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.cappuccino.common.enumeration.GroupLevel;
import com.blcheung.cappuccino.dto.cms.*;
import com.blcheung.cappuccino.model.CmsUserDO;
import com.blcheung.cappuccino.vo.cms.*;
import com.blcheung.cappuccino.vo.common.PagingVO;

import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/21 9:45 下午
 */
public interface CmsAdminService {

    /**
     * 检查用户是否为管理员级别
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/11 9:50 下午
     */
    Boolean checkUserIsAdmin(Long userId);

    /**
     * 新建分组
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/21 9:47 下午
     */
    Boolean createGroup(NewGroupDTO dto);

    /**
     * 更新分组
     *
     * @param groupId
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 4:18 上午
     */
    Boolean updateGroup(Long groupId, UpdateGroupDTO dto);

    /**
     * 删除分组
     *
     * @param groupId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/26 4:03 上午
     */
    Boolean deleteGroup(Long groupId);

    /**
     * 获取所有用户分组（不包括超级管理员）
     *
     * @return java.util.List<com.blcheung.cappuccino.model.CmsGroupDO>
     * @author BLCheung
     * @date 2022/1/21 2:09 上午
     */
    List<GroupVO> getAllUserLevelGroups();

    /**
     * 获取所有可分配的权限
     * @author BLCheung
     * @date 2022/4/18 8:20 下午
     * @return java.util.List<com.blcheung.cappuccino.vo.cms.PermissionModuleVO>
     */
    List<PermissionModuleVO> getAssignablePermissions();

    /**
     * 获取一个分组以及它的权限
     *
     * @param groupId
     * @return com.blcheung.cappuccino.vo.cms.GroupPermissionVO
     * @author BLCheung
     * @date 2022/1/21 11:25 下午
     */
    GroupPermissionVO getGroupAndPermissions(Long groupId);

    /**
     * 分组分配多个权限
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/22 2:39 上午
     */
    Boolean dispatchPermissions(DispatchPermissionsDTO dto);

    /**
     * 获取用户分页
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.PagingResultVO<com.blcheung.cappuccino.vo.cms.UserVO>
     * @author BLCheung
     * @date 2022/1/24 11:22 下午
     */
    PagingVO<UserGroupVO> getUserPage(QueryUsersDTO dto);

    /**
     * 更新用户分组
     *
     * @param dto
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/25 4:18 上午
     */
    Boolean updateUserGroup(Long userId, UpdateUserGroupDTO dto);

    /**
     * 更改用户密码
     *
     * @param userId
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 5:40 上午
     */
    Boolean changeUserPassword(Long userId, ResetUserPasswordDTO dto);

    /**
     * 删除用户
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 9:45 下午
     */
    Boolean deleteUser(Long userId);

    /**
     * 用于组装管理员模块的查看用户分页结果通用函数（给每个用户数据拼接上所属分组信息）
     *
     * @param pageable
     * @param filterLevel 过滤的级别
     * @return com.blcheung.cappuccino.vo.PagingResultVO<com.blcheung.cappuccino.vo.cms.UserGroupVO>
     * @author BLCheung
     * @date 2022/1/26 3:44 上午
     */
    PagingVO<UserGroupVO> assembleUserGroupVO(Page<CmsUserDO> pageable, GroupLevel filterLevel);
}
