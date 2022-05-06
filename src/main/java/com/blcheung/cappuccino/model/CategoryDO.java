package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-04
 */
@Getter
@Setter
@TableName("category")
public class CategoryDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private Integer isRoot;

    private Long parentId;

    private String img;

    @TableField(value = "`index`")
    private Integer index;

    private Integer online;

    private Integer level;

}
