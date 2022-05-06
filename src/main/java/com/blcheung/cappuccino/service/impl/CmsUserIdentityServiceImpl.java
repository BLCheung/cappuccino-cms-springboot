package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.enumeration.UserIdentifyType;
import com.blcheung.cappuccino.common.exceptions.DatabaseActionException;
import com.blcheung.cappuccino.common.token.DoubleJWT;
import com.blcheung.cappuccino.common.token.Tokens;
import com.blcheung.cappuccino.mapper.CmsUserIdentityMapper;
import com.blcheung.cappuccino.model.CmsUserIdentityDO;
import com.blcheung.cappuccino.service.CmsUserIdentityService;
import com.blcheung.cappuccino.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

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
    public Boolean changeUserPasswordIdentity(Long userId, String password) {
        String newPassword = EncryptUtil.encrypt(password);
        return this.lambdaUpdate()
                   .eq(CmsUserIdentityDO::getIdentityType, UserIdentifyType.USERNAME_PASSWORD)
                   .eq(CmsUserIdentityDO::getUserId, userId)
                   .set(CmsUserIdentityDO::getCredential, newPassword)
                   .update();
    }

    @Override
    public Boolean changeUserNameIdentity(Long userId, String userName) {
        return this.lambdaUpdate()
                   .eq(CmsUserIdentityDO::getUserId, userId)
                   .set(CmsUserIdentityDO::getIdentifier, userName)
                   .update();
    }

    @Override
    public Boolean removeUserIdentity(Long userId) {
        if (ObjectUtils.isEmpty(userId)) return false;
        return this.getBaseMapper()
                   .deleteByUserId(userId) > 0;
    }

    @Override
    public Optional<CmsUserIdentityDO> verifyUserNamePasswordIdentity(Long userId, String username, String password) {
        CmsUserIdentityDO cmsUserIdentityDO = this.lambdaQuery()
                                                  .eq(CmsUserIdentityDO::getUserId, userId)
                                                  .eq(CmsUserIdentityDO::getIdentityType,
                                                      UserIdentifyType.USERNAME_PASSWORD.getValue())
                                                  .eq(CmsUserIdentityDO::getIdentifier, username)
                                                  .one();
        return Optional.ofNullable(cmsUserIdentityDO)
                       .filter(ui -> this.verifyCredential(ui.getCredential(), password));
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
    public Boolean verifyCredential(String credential, String key) {
        if (StringUtils.isEmpty(credential) || StringUtils.isEmpty(key)) return false;
        return EncryptUtil.decrypt(credential, key);
    }

    @Override
    public Boolean saveUserIdentity(CmsUserIdentityDO cmsUserIdentityDO) {
        return this.save(cmsUserIdentityDO);
    }
}
