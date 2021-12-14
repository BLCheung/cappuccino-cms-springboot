package com.blcheung.zblmissyouadmin.common.enumeration;

import lombok.Getter;

/**
 * @author BLCheung
 * @date 2021/12/15 1:12 上午
 */
@Getter
public enum TokenType {
    ACCESS(1, "access token"),
    REFRESH(2, "refresh type");


    private final Integer value;
    private final String  desc;

    TokenType(Integer value, String desc) {
        this.value = value;
        this.desc  = desc;
    }
}
