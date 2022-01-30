package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.cms.NewAdminGroupDTO;
import com.blcheung.zblmissyouadmin.dto.cms.QueryUsersDTO;
import com.blcheung.zblmissyouadmin.dto.cms.UpdateUserGroupDTO;
import com.blcheung.zblmissyouadmin.vo.cms.GroupVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserGroupVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/26 10:02 下午
 */
public interface CmsRootService {

    /**
     * 检查用户是否为超级管理员级别
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/11 10:24 下午
     */
    Boolean checkUserIsRoot(Long userId);

    /**
     * 新建管理员分组
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/26 10:01 下午
     */
    Boolean createAdminGroup(NewAdminGroupDTO dto);

    /**
     * 删除管理员分组
     *
     * @param adminGroupId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/26 10:01 下午
     */
    Boolean deleteAdminGroup(Long adminGroupId);

    /**
     * 超级管理员获取所有用户与管理员
     *
     * @param dto
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.cms.UserVO>
     * @author BLCheung
     * @date 2022/1/25 3:00 上午
     */
    PagingVO<UserGroupVO> getAllUserByRoot(QueryUsersDTO dto);

    /**
     * 超级管理员获取所有分组
     *
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.cms.GroupVO>
     * @author BLCheung
     * @date 2022/1/25 3:35 上午
     */
    List<GroupVO> getAllGroupByRoot();

    /**
     * 超级管理员更新用户信息（分组）
     *
     * @param userId
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/26 9:02 下午
     */
    Boolean updateUserGroupByRoot(Long userId, UpdateUserGroupDTO dto);
}
