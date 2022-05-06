package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格值实体
 *
 * @author BLCheung
 * @since 2022-02-15
 */
@Getter
@Setter
@TableName("spec_value")
public class SpecValueDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 规格值
     */
    private String value;

    /**
     * 所属规格名的id
     */
    private Long specId;

    private String extend;

}
