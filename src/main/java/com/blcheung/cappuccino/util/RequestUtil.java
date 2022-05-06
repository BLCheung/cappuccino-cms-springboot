package com.blcheung.cappuccino.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 *
 * @author BLCheung
 * @date 2021/12/2 12:59 上午
 */
public class RequestUtil {

    /**
     * 获取当前request请求
     *
     * @return javax.servlet.http.HttpServletRequest
     * @author BLCheung
     * @date 2021/12/2 1:05 上午
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 当前线程没有绑定request
        if (requestAttributes == null) return null;

        if (requestAttributes instanceof ServletRequestAttributes) {
            return ( (ServletRequestAttributes) requestAttributes ).getRequest();
        }

        return null;
    }

    /**
     * 获取当前request请求url
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/2 1:08 上午
     */
    public static String getRequestUrl() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) return null;

        return request.getServletPath();
    }

    /**
     * 获取指定request请求url
     *
     * @param request
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/2 1:08 上午
     */
    public static String getRequestUrl(HttpServletRequest request) {
        if (request == null) return null;

        return request.getServletPath();
    }

    /**
     * 获取当前request行为请求url
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/2 2:35 上午
     */
    public static String getActionRequest() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) return null;

        return request.getMethod() + " " + request.getServletPath();
    }

    /**
     * 获取特定request行为请求url
     *
     * @param request
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/2 2:37 上午
     */
    public static String getActionRequest(HttpServletRequest request) {
        if (request == null) return null;

        return request.getMethod() + " " + request.getServletPath();
    }
}
