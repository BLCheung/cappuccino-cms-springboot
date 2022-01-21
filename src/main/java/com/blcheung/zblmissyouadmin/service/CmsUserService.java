package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.dto.cms.LoginDTO;
import com.blcheung.zblmissyouadmin.dto.cms.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsUserService extends IService<CmsUserDO> {
    /**
     * 注册用户
     *
     * @param registerUserDTO
     * @return com.blcheung.zblmissyouadmin.model.CmsUserDO
     * @author BLCheung
     * @date 2021/12/13 9:03 下午
     */
    CmsUserDO registerUser(RegisterUserDTO registerUserDTO);

    /**
     * 登录
     *
     * @param loginDTO
     * @return com.blcheung.zblmissyouadmin.common.token.Tokens
     * @author BLCheung
     * @date 2021/12/30 10:37 下午
     */
    Tokens login(LoginDTO loginDTO);

    /**
     * 通过用户名检查用户是否存在
     *
     * @param username
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/13 9:03 下午
     */
    Boolean checkUserExistByUserName(String username);

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/17 10:54 下午
     */
    Boolean checkUserExistByEmail(String email);

    /**
     * 通过用户名获取用户
     *
     * @param userName
     * @return java.util.Optional<com.blcheung.zblmissyouadmin.model.CmsUserDO>
     * @author BLCheung
     * @date 2021/12/30 10:42 下午
     */
    Optional<CmsUserDO> getUserByUserName(String userName);

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
     * 检查用户是否为超级管理员级别
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/11 10:24 下午
     */
    Boolean checkUserIsRoot(Long userId);

    /**
     * 获取用户拥有的权限
     *
     * @param uid
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/11 2:42 上午
     */
    List<CmsPermissionDO> getUserPermissions(Long uid);
}
