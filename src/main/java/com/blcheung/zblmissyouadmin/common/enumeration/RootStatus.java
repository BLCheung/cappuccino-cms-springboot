package com.blcheung.zblmissyouadmin.common.enumeration;

import lombok.Getter;

/**
 * @author BLCheung
 * @date 2022/2/5 12:00 上午
 */
public enum RootStatus {
    ROOT(1, "父类"),
    NOT_ROOT(0, "非父类");

    @Getter
    private final Integer value;

    RootStatus(Integer value, String desc) {
        this.value = value;
    }
}
