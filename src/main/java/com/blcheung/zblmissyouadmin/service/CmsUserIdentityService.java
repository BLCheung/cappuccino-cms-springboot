package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.common.enumeration.UserIdentifyType;
import com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
