package com.blcheung.zblmissyouadmin.common.resolver;

import com.blcheung.zblmissyouadmin.common.bean.RouterInfo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限认证处理器
 *
 * @author BLCheung
 * @date 2021/12/31 9:42 下午
 */
public interface AuthorizeResolver {

    /**
     * 处理需要超级管理员访问的情况
     *
     * @param request
     * @param response
     * @param routerInfo
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/9 2:38 上午
     */
    Boolean handleRoot(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo);

    /**
     * 处理需要管理员访问的情况
     *
     * @param request
     * @param response
     * @param routerInfo
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/9 2:38 上午
     */
    Boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo);

    /**
     * 处理需要归属相应用户群组下才可访问的情况
     *
     * @param request
     * @param response
     * @param routerInfo
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/9 2:38 上午
     */
    Boolean handleGroup(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo);

    /**
     * 处理需要登录后才可访问的情况
     *
     * @param request
     * @param response
     * @param routerInfo
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/9 2:38 上午
     */
    Boolean handleLogin(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo);

    /**
     * 处理刷新令牌的情况
     *
     * @param request
     * @param response
     * @param routerInfo
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/9 2:38 上午
     */
    Boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, RouterInfo routerInfo);

    /**
     * 访问前的前置处理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @author BLCheung
     * @date 2022/1/9 2:39 上午
     */
    default Boolean handlePreHandle(HttpServletRequest request, HttpServletResponse response,
                                    HandlerMethod handlerMethod) {
        return false;
    }

    /**
     * 访问后的后置处理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @author BLCheung
     * @date 2022/1/9 2:39 上午
     */
    default void handlePostHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                                  ModelAndView modelAndView) {}

    /**
     * 已完成访问后的处理
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @author BLCheung
     * @date 2022/1/9 2:39 上午
     */
    default void handleAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                       Exception ex) {}

    /**
     * 没有找到任何方法的处理
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @author BLCheung
     * @date 2022/1/9 2:39 上午
     */
    default Boolean handleNoHandle(HttpServletRequest request, HttpServletResponse response, Object handle) {
        return true;
    }
}
