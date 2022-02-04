package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-03
 */
@Getter
@Setter
@TableName("activity")
public class ActivityDO extends BaseDO {

    private static final long serialVersionUID = 1L;

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
