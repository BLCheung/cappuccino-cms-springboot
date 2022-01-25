package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.QueryUsersDTO;
import com.blcheung.zblmissyouadmin.dto.cms.DispatchPermissionsDTO;
import com.blcheung.zblmissyouadmin.dto.cms.NewGroupDTO;
import com.blcheung.zblmissyouadmin.dto.cms.UpdateUserGroupDTO;
import com.blcheung.zblmissyouadmin.vo.PagingResultVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupPermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserVO;

import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/21 9:45 下午
 */
public interface CmsAdminService {

    /**
     * 新建分组
     *
     * @param dto
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 9:47 下午
     */
    boolean createGroup(NewGroupDTO dto);

    /**
     * 删除分组
     *
     * @param groupId
     * @return boolean
     * @author BLCheung
     * @date 2021/12/26 4:03 上午
     */
    boolean deleteGroup(Long groupId);

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
    PagingResultVO<UserVO> getUserPage(QueryUsersDTO dto);

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
}
