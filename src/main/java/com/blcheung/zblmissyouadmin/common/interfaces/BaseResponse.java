package com.blcheung.zblmissyouadmin.common.interfaces;

/**
 * @author BLCheung
 * @date 2021/12/2 12:03 上午
 */
public interface BaseResponse {

    /**
     * 获取错误码
     *
     * @return java.lang.Integer
     * @author BLCheung
     * @date 2021/12/2 12:06 上午
     */
    Integer getCode();

    /**
     * 获取Http状态码
     *
     * @return java.lang.Integer
     * @author BLCheung
     * @date 2021/12/2 12:07 上午
     */
    Integer getStatusCode();

    /**
     * 获取自定义的错误消息
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/1/18 7:18 上午
     */
    String getMsg();
}
