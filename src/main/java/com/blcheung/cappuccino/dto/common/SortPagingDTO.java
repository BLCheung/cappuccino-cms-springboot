package com.blcheung.cappuccino.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页字段排序实体DTO
 *
 * @author BLCheung
 * @date 2022/4/10 1:22 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SortPagingDTO extends BasePagingDTO {
    /**
     * 排序的字段属性
     */
    private String prop;

    /**
     * 排序，升序或降序
     */
    private String order;
}
