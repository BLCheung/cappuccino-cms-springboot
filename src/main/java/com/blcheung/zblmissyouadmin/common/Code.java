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

    Fail(10000, "未知错误");

    private final Integer code;
    private final String  desc;

    Code(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
