package com.blcheung.cappuccino.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/22 12:08 上午
 */
@Getter
@Setter
public class RootCategoryVO extends CategoryVO {

    private List<CategoryVO> items;

    public RootCategoryVO(List<CategoryVO> items) {
        this.items = items;
    }
}
