package com.blcheung.zblmissyouadmin.common.resolver.impl;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.blcheung.zblmissyouadmin.common.bean.PermissionMetaCollector;
import com.blcheung.zblmissyouadmin.common.bean.RouterInfo;
import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.TokenExpiredException;
import com.blcheung.zblmissyouadmin.common.exceptions.TokenInvalidException;
import com.blcheung.zblmissyouadmin.common.exceptions.UnAuthorizedException;
import com.blcheung.zblmissyouadmin.common.resolver.AuthorizeResolver;
import com.blcheung.zblmissyouadmin.common.token.DoubleJWT;
import com.blcheung.zblmissyouadmin.kit.UserKit;
import com.blcheung.zblmissyouadmin.kit.UserLevelKit;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.service.CmsAdminService;
import com.blcheung.zblmissyouadmin.service.CmsRootService;
import com.blcheung.zblmissyouadmin.service.CmsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author BLCheung
 * @date 2022/1/9 2:19 上午
 */
@Component
public class AuthorizeResolverImpl implements AuthorizeResolver {
    @Autowired
    private DoubleJWT jwt;

    @Autowired
    private CmsRootService cmsRootService;

    @Autowired
    private CmsAdminService cmsAdminService;

    @Autowired
    private CmsUserService cmsUserService;

    @Autowired
    private PermissionMetaCollector permissionMetaCollector;

    private final String TOKEN_KEY = "Authorization";

    @Override
    public Boolean handlePreHandle(HttpServletRequest request, HttpServletResponse response,
                                   HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        String clazzName = method.getDeclaringClass()
                                 .getName();
        String methodName = method.getName();

        // 路由唯一标识
        String identity = clazzName + "#" + methodName;

        RouterInfo routerInfo = this.permissionMetaCollector.findRouterInfo(identity);
        if (ObjectUtils.isEmpty(routerInfo)) {
            // 没有收集的路由的权限可能为ROOT，ADMIN或LOGIN级别的路由
            return this.handleNoRouter(request, response, handlerMethod);
        } else {
            return this.handleRouter(request, response, routerInfo);
        }
    }

    @Override
    public Boolean handleLogin(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        String token = this.verifyHeader(request);
        Map<String, Claim> claimMap;
        try {
            claimMap = this.jwt.decodeAccessToken(token);
        } catch (JWTDecodeException | AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new TokenInvalidException(10012);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            throw new TokenExpiredException(10013);
        }

        return this.verifyClaim(claimMap);
    }

    @Override
    public Boolean handleGroup(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        this.handleLogin(request, response, routerInfo);

        CmsUserDO cmsUserDO = UserKit.getUser();
        // 管理员及以上级别可直接访问
        if (this.verifyAdmin(cmsUserDO.getId())) return true;

        List<CmsPermissionDO> userPermissions = this.cmsUserService.getUserPermissions(cmsUserDO.getId());
        if (userPermissions.isEmpty()) throw new UnAuthorizedException();

        boolean isAuthorized = userPermissions.stream()
                                              .anyMatch(cmsPermissionDO -> this.verifyPermission(cmsPermissionDO,
                                                                                                 routerInfo));
        if (!isAuthorized) throw new UnAuthorizedException();

        return true;
    }

    @Override
    public Boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        this.handleLogin(request, response, routerInfo);

        CmsUserDO cmsUserDO = UserKit.getUser();
        // 管理员及以上级别可直接访问
        if (!this.verifyAdmin(cmsUserDO.getId())) throw new UnAuthorizedException();

        return true;
    }

    @Override
    public Boolean handleRoot(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        this.handleLogin(request, response, routerInfo);

        CmsUserDO cmsUserDO = UserKit.getUser();
        // 超级管理员级别才可直接访问
        if (!this.verifyRoot(cmsUserDO.getId())) throw new UnAuthorizedException();

        return true;
    }

    @Override
    public Boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        String refreshToken = this.verifyHeader(request);
        Map<String, Claim> claimMap;
        try {
            claimMap = this.jwt.decodeRefreshToken(refreshToken);
        } catch (JWTDecodeException | AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new TokenInvalidException(10012);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            throw new TokenExpiredException(10013);
        }

        return this.verifyClaim(claimMap);
    }

    @Override
    public void handleAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                      Exception ex) {
        // 请求结束后，清除线程池内当前线程的用户
        UserKit.clearUser();
    }

    @Override
    public Boolean handleNoHandle(HttpServletRequest request, HttpServletResponse response, Object handle) {
        return true;
    }


    private Boolean handleNoRouter(HttpServletRequest request, HttpServletResponse response,
                                   HandlerMethod handlerMethod) {
        UserLevel userLevel = UserLevelKit.findUserLevel(handlerMethod.getMethod());

        return switch (userLevel) {
            case LOGIN, GROUP -> this.handleLogin(request, response, null);

            case ADMIN -> this.handleAdmin(request, response, null);

            case ROOT -> this.handleRoot(request, response, null);

            case REFRESH -> this.handleRefresh(request, response, null);

            default -> true;
        };
    }

    private Boolean handleRouter(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo) {
        UserLevel userLevel = routerInfo.getLevel();

        return switch (userLevel) {
            case LOGIN -> this.handleLogin(request, response, routerInfo);

            case GROUP -> this.handleGroup(request, response, routerInfo);

            case ADMIN -> this.handleAdmin(request, response, routerInfo);

            case ROOT -> this.handleRoot(request, response, routerInfo);

            case REFRESH -> this.handleRefresh(request, response, routerInfo);

            default -> true;
        };
    }

    private String verifyHeader(HttpServletRequest request) {
        // 处理Http头部部分是否带有token参数
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isBlank(token)) throw new UnAuthorizedException(10009);

        return token;
    }

    private Boolean verifyClaim(Map<String, Claim> claimMap) {
        if (ObjectUtils.isEmpty(claimMap)) throw new TokenInvalidException(10012);

        Long userId = claimMap.get(DoubleJWT.KEY_TOKEN_IDENTITY)
                              .asLong();
        CmsUserDO cmsUserDO = this.cmsUserService.getById(userId);
        if (ObjectUtils.isEmpty(cmsUserDO)) throw new NotFoundException(10103);

        // 保存到用户线程池
        UserKit.setUser(cmsUserDO);

        return true;
    }

    private Boolean verifyAdmin(Long userId) {
        return this.cmsAdminService.checkUserIsAdmin(userId);
    }

    private Boolean verifyRoot(Long userId) {
        return this.cmsRootService.checkUserIsRoot(userId);
    }

    private Boolean verifyPermission(CmsPermissionDO cmsPermissionDO, RouterInfo routerInfo) {
        return StringUtils.equals(cmsPermissionDO.getModule(), routerInfo.getModule()) &&
               StringUtils.equals(cmsPermissionDO.getName(), routerInfo.getRouter());
    }
}
