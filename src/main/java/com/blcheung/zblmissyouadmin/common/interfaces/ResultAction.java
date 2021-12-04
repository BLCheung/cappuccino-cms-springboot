package com.blcheung.zblmissyouadmin.common.interfaces;

/**
 * @author BLCheung
 * @date 2021/12/2 12:34 上午
 */
public interface ResultAction {

    /**
     * 获取请求行为request
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/2 12:35 上午
     */
    String getRequest();

    /**
     * 设置请求行为request
     *
     * @author BLCheung
     * @date 2021/12/2 12:35 上午
     */
    void setRequest();

    /**
     * 设置请求行为request
     *
     * @param request
     * @author BLCheung
     * @date 2021/12/2 12:35 上午
     */
    void setRequest(String request);
}
