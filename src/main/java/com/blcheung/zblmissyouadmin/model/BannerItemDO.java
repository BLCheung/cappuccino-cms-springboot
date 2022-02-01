package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.zblmissyouadmin.model.BaseDO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-31
 */
@Getter
@Setter
@TableName("banner_item")
public class BannerItemDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String img;

    private String keyword;

    private Integer type;

    private Long bannerId;

    private String name;
}
