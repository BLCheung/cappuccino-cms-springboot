package com.blcheung.cappuccino.dto;

import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author BLCheung
 * @date 2022/2/27 11:48 下午
 */
@Getter
@Setter
public class OrderPageDTO extends BasePagingDTO {

    @Length(max = 100)
    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
}
