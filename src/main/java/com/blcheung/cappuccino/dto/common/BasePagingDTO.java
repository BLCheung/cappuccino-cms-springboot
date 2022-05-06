package com.blcheung.cappuccino.dto.common;

import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 分页DTO基类
 *
 * @author BLCheung
 * @date 2022/1/23 8:55 下午
 */
@Setter
public class BasePagingDTO {

    @Min(value = 1, message = "{page.num.min}")
    private Long pageNum;

    @Min(value = 1, message = "{page.size.min}")
    @Max(value = 30, message = "{page.size.max}")
    private Long pageSize;

    public Long getPageNum() {
        return null == pageNum ? 1 : pageNum;
    }

    public Long getPageSize() {
        return null == pageSize ? 10 : pageSize;
    }
}
