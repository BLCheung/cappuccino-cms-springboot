package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.common.enumeration.UserIdentifyType;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO;

import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsUserIdentityService extends IService<CmsUserIdentityDO> {

    /**
     * 插入用户凭证到数据库
     *
     * @param cmsUserIdentityDO
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/29 10:56 下午
     */
    Boolean saveUserIdentity(CmsUserIdentityDO cmsUserIdentityDO);

    /**
     * 创建用户凭证原始方法
     *
     * @param userId
     * @param identifier
     * @param credential
     * @param userIdentifyType
     * @return com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO
     * @author BLCheung
     * @date 2021/12/29 10:43 下午
     */
    CmsUserIdentityDO createIdentity(Long userId, String identifier, String credential,
                                     UserIdentifyType userIdentifyType);

    /**
     * 校验凭证秘钥是否正确
     *
     * @param credential
     * @param key
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/28 10:06 下午
     */
    Boolean verifyCredential(String credential, String key);

    /**
     * 创建用户名密码类型的用户凭证
     *
     * @param userId
     * @param username
     * @param password
     * @return com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO
     * @author BLCheung
     * @date 2021/12/29 10:45 下午
     */
    CmsUserIdentityDO createUsernamePasswordIdentity(Long userId, String username, String password);

    /**
     * 更改用户凭证的用户密码
     *
     * @param userId
     * @param password
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 5:56 上午
     */
    Boolean changeUserPasswordIdentity(Long userId, String password);

    /**
     * 更改用户凭证的用户名
     *
     * @param userId
     * @param userName
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/28 1:15 上午
     */
    Boolean changeUserNameIdentity(Long userId, String userName);

    /**
     * 删除用户名密码类型的用户凭证
     *
     * @param userId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 10:01 下午
     */
    Boolean removeUserIdentity(Long userId);

    /**
     * 校验用户名密码凭证
     *
     * @param userId
     * @param username
     * @param password
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/30 11:10 下午
     */
    Optional<CmsUserIdentityDO> verifyUserNamePasswordIdentity(Long userId, String username, String password);

    /**
     * 生成双令牌
     *
     * @param identity
     * @return com.blcheung.zblmissyouadmin.common.token.Tokens
     * @author BLCheung
     * @date 2021/12/30 11:21 下午
     */
    Tokens generateDoubleJwtToken(Long identity);
}
