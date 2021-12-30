package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.UserIdentifyType;
import com.blcheung.zblmissyouadmin.common.exceptions.DatabaseActionException;
import com.blcheung.zblmissyouadmin.common.token.DoubleJWT;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.mapper.CmsUserIdentityMapper;
import com.blcheung.zblmissyouadmin.model.CmsUserIdentityDO;
import com.blcheung.zblmissyouadmin.service.CmsUserIdentityService;
import com.blcheung.zblmissyouadmin.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsUserIdentityServiceImpl extends ServiceImpl<CmsUserIdentityMapper, CmsUserIdentityDO>
        implements CmsUserIdentityService {

    @Autowired
    private DoubleJWT jwt;

    @Override
    public CmsUserIdentityDO createUsernamePasswordIdentity(Long userId, String username, String password) {
        String encryptPassword = EncryptUtil.encrypt(password);
        return this.createIdentity(userId, username, encryptPassword, UserIdentifyType.USERNAME_PASSWORD);
    }

    @Override
    public Boolean verifyUserNamePasswordIdentity(Long userId, String username, String password) {
        CmsUserIdentityDO cmsUserIdentityDO = this.lambdaQuery()
                                                  .eq(CmsUserIdentityDO::getUserId, userId)
                                                  .eq(CmsUserIdentityDO::getIdentityType,
                                                      UserIdentifyType.USERNAME_PASSWORD.getValue())
                                                  .eq(CmsUserIdentityDO::getIdentifier, username)
                                                  .one();
        return EncryptUtil.decrypt(cmsUserIdentityDO.getCredential(), password);
    }

    @Override
    public Tokens generateDoubleJwtToken(Long identity) {
        return jwt.createTokens(identity);
    }

    @Override
    public CmsUserIdentityDO createIdentity(Long userId, String identifier, String credential,
                                            UserIdentifyType userIdentifyType) {
        CmsUserIdentityDO cmsUserIdentityDO = CmsUserIdentityDO.builder()
                                                               .userId(userId)
                                                               .identifier(identifier)
                                                               .credential(credential)
                                                               .identityType(userIdentifyType)
                                                               .build();

        Boolean saveSuccess = this.saveUserIdentity(cmsUserIdentityDO);
        if (!saveSuccess) throw new DatabaseActionException(10120);

        return cmsUserIdentityDO;
    }

    @Override
    public Boolean saveUserIdentity(CmsUserIdentityDO cmsUserIdentityDO) {
        return this.save(cmsUserIdentityDO);
    }
}
