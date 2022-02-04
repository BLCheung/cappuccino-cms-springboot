package com.blcheung.zblmissyouadmin.common.enumeration;

import lombok.Getter;

/**
 * @author BLCheung
 * @date 2022/2/3 9:54 下午
 */
public enum OnlineStatus {
    ONLINE(1, "上线"),
    OFFLINE(0, "下线");

    @Getter
    private final Integer value;

    OnlineStatus(Integer value, String desc) {
        this.value = value;
    }
}
