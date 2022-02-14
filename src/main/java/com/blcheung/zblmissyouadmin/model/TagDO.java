package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Getter
@Setter
@TableName("tag")
public class TagDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 中文限制6个，英文限制12个，由逻辑层控制
     */
    private String title;

    private String description;

    private Integer highlight;

    private Integer type;


}
