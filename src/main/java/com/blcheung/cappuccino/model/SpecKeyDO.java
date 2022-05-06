package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 规格名实体
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("spec_key")
public class SpecKeyDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

}
