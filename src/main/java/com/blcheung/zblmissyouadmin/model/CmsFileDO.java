package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
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
@TableName("cms_file")
public class CmsFileDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String path;

    /**
     * LOCAL 本地，REMOTE 远程
     */
    private String type;

    private String name;

    private String extension;

    private Integer size;

    /**
     * md5值，防止上传重复文件
     */
    private String md5;


}
