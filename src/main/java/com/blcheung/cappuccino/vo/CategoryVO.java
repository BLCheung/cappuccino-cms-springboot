package com.blcheung.cappuccino.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/5 12:08 上午
 */
@Getter
@Setter
public class CategoryVO {

    private Long id;

    private String name;

    private String description;

    private Integer isRoot;

    private Long parentId;

    private String img;

    private Integer index;

    private Integer online;

    private Integer level;
}
