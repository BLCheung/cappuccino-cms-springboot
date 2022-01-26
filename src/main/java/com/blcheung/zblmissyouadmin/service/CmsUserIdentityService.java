package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.common.enumeration.UserIdentifyType;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO;

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
     * 更改用户名密码类型的用户密码
     *
     * @param userId
     * @param password
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 5:56 上午
     */
    Boolean changeUserPasswordIdentity(Long userId, String password);

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
    Boolean verifyUserNamePasswordIdentity(Long userId, String username, String password);

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
