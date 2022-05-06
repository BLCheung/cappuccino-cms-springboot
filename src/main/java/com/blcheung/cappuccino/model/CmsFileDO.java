package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * cms文件实体
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("cms_file")
public class CmsFileDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String path;

    /**
     * LOCAL 本地，REMOTE 远程
     */
    private String type;

    /**
     * 文件url
     */
    private String url;

    /**
     * 后端分配的文件名
     */
    private String name;

    /**
     * 原始文件名
     */
    private String originalName;

    private String extension;

    private Integer size;

    /**
     * md5值，防止上传重复文件
     */
    private String md5;


}
