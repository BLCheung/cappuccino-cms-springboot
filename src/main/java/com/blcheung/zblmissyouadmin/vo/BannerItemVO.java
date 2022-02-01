package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/1/31 11:25 下午
 */
@Getter
@Setter
public class BannerItemVO {

    private Long id;

    private String img;

    private String keyword;

    private Integer type;

    private Long bannerId;

    private String name;
}
