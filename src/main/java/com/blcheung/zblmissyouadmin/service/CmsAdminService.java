package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.dto.cms.*;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupPermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;

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
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsGroupDO>
     * @author BLCheung
     * @date 2022/1/21 2:09 上午
     */
    List<GroupVO> getAllUserLevelGroups();

    /**
     * 获取所有可分配的权限
     *
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/21 10:09 下午
     */
    List<PermissionVO> getAssignablePermissions();

    /**
     * 获取一个分组以及它的权限
     *
     * @param groupId
     * @return com.blcheung.zblmissyouadmin.vo.cms.GroupPermissionVO
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
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<com.blcheung.zblmissyouadmin.vo.cms.UserVO>
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
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO>
     * @author BLCheung
     * @date 2022/1/26 3:44 上午
     */
    PagingVO<UserGroupVO> assembleUserGroupVO(Page<CmsUserDO> pageable, GroupLevel filterLevel);
}
