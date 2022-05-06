package com.blcheung.cappuccino.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/7 11:58 下午
 */
@Getter
@Setter
public class ThemeVO {

    private Long id;

    private String title;

    private String description;

    private String name;

    private String tplName;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;

}
