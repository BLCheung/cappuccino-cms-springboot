package com.blcheung.zblmissyouadmin.module.file.bean;

import lombok.*;

/**
 * @author BLCheung
 * @date 2022/1/15 3:12 上午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {
    /**
     * 文件的名称
     */
    private String name;

    /**
     * 文件原始名称
     */
    private String originalName;

    /**
     * 文件真实url
     */
    private String url;

    /**
     * 若为本地上传类型，则为文件本地路径，否则为服务器存放资源路径
     */
    private String path;

    /**
     * 文件扩展名，.png
     */
    private String extension;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件md5，防重
     */
    private String md5;

    /**
     * 文件上传类型
     */
    private String type;
}
