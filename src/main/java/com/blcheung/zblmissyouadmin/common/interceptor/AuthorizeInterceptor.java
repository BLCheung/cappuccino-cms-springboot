package com.blcheung.zblmissyouadmin.common.interceptor;

import com.blcheung.zblmissyouadmin.common.bean.PermissionMetaCollector;
import com.blcheung.zblmissyouadmin.common.resolver.AuthorizeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BLCheung
 * @date 2022/1/1 2:16 上午
 */
public class AuthorizeInterceptor implements AsyncHandlerInterceptor {

    @Autowired
    private AuthorizeResolver authorizeResolver;

    public AuthorizeInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return this.authorizeResolver.handlePreHandle(request, response, handlerMethod);
        } else {
            // handler不是HandlerMethod的情况
            return this.authorizeResolver.handleNoHandle(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        this.authorizeResolver.handlePostHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        this.authorizeResolver.handleAfterCompletion(request, response, handler, ex);
    }
}
