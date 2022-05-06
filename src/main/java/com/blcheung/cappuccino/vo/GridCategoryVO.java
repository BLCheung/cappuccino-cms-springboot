package com.blcheung.cappuccino.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/6 10:56 下午
 */
@Getter
@Setter
public class GridCategoryVO {

    private Long id;

    private String title;

    private String img;

    private String name;

    private Long categoryId;

    private Long rootCategoryId;
}
