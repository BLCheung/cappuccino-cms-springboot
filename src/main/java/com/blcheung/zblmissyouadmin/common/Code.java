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

    UNAUTHORIZED(10001, "权限不足"),

    FORBIDDEN(10002, "禁止操作"),

    PARAMETER_ERROR(10003, "参数错误"),

    NOT_FOUND(10004, "资源找不到");


    private final Integer code;
    private final String  desc;

    Code(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
