package com.blcheung.zblmissyouadmin.module.file.enumeration;

import lombok.Getter;

/**
 * 文件上传类型
 *
 * @author BLCheung
 * @date 2022/1/16 2:51 上午
 */
public enum UploadType {
    LOCAL(0, "本地上传"),
    REMOTE(1, "远程上传");

    @Getter
    private final Integer value;

    UploadType(Integer value, String desc) {
        this.value = value;
    }
}
