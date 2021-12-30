package com.blcheung.zblmissyouadmin.common;

import lombok.Getter;

/**
 * @author BLCheung
 * @date 2021/12/1 11:40 下午
 */
@Getter
public enum Code {
    OK(0, "OK"),

    CREATED(1, "创建成功"),

    UPDATED(2, "更新成功"),

    DELETED(3, "删除成功"),

    INTERNAL_SERVER_ERROR(9999, "服务器异常"),

    FAIL(10000, "操作失败"),

    UNAUTHORIZED(10001, "未经授权"),

    FORBIDDEN(10002, "禁止操作"),

    PARAMETER_ERROR(10003, "参数错误"),

    NOT_FOUND(10004, "资源找不到"),

    ARGUMENT_TYPE_ERROR(10005, "参数类型错误"),

    MISSING_REQUEST_PARAMETER_ERROR(10006, "请求参数丢失"),

    MISSING_REQUEST_BODY_ERROR(10007, "请求体body丢失"),

    METHOD_NOT_FOUND(10008, "找不到相应api"),

    MISSING_HEADER_ERROR(10009, "请传入认证头字段"),

    TOKEN_INVALID(10010, "令牌失效"),

    TOKEN_EXPIRED(10011, "令牌过期");

    private final Integer code;
    private final String  desc;

    Code(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
