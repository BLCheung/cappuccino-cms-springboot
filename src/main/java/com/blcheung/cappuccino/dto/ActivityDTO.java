package com.blcheung.cappuccino.dto;

import com.blcheung.cappuccino.common.enumeration.OnlineStatus;
import com.blcheung.cappuccino.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/3 8:06 下午
 */
@Getter
@Setter
public class ActivityDTO {

    @NotBlank
    @Length(min = 1, max = 60)
    private String title;

    @NotBlank
    @Length(min = 1, max = 20)
    private String name;

    @Length(max = 255)
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date startTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date endTime;

    @Length(max = 60)
    private String remark;

    @Enum(target = OnlineStatus.class, allowNull = false)
    private Integer online;

    @NotBlank
    @Length(min = 1, max = 255)
    private String entranceImg;

    @NotBlank
    @Length(min = 1, max = 255)
    private String internalTopImg;

    private List<@Min(1) Long> couponIds;

}
