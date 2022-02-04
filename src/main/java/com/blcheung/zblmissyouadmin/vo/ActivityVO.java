package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author BLCheung
 * @date 2022/2/3 10:01 下午
 */
@Getter
@Setter
public class ActivityVO {

    private Long id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer online;

    private String entranceImg;

    private String internalTopImg;

    private String name;
}
