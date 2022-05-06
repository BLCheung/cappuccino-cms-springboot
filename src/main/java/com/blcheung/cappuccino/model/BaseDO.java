package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author BLCheung
 * @date 2021/11/28 12:34 上午
 */
@Data
public class BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonIgnore
    private Date createTime;

    @TableField(update = "now()")
    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;
}
