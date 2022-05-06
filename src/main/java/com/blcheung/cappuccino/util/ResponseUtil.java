package com.blcheung.cappuccino.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * 响应结果工具类
 *
 * @author BLCheung
 * @date 2021/12/2 1:00 上午
 */
public class ResponseUtil {

    /**
     * 获取当前响应
     *
     * @return javax.servlet.http.HttpServletResponse
     * @author BLCheung
     * @date 2021/12/2 2:41 上午
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) return null;

        if (requestAttributes instanceof ServletRequestAttributes) {
            return ( (ServletRequestAttributes) requestAttributes ).getResponse();
        }

        return null;
    }

    /**
     * 设置当前响应的http状态码
     *
     * @param httpStatus
     * @author BLCheung
     * @date 2021/12/2 2:45 上午
     */
    public static void setCurrentResponseStatusCode(int httpStatus) {
        HttpServletResponse response = ResponseUtil.getResponse();
        if (response == null) return;

        response.setStatus(httpStatus);
    }
}
