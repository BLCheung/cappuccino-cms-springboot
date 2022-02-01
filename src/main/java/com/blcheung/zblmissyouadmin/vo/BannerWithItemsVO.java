package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/31 11:22 下午
 */
@Getter
@Setter
public class BannerWithItemsVO extends BannerVO {

    private List<BannerItemVO> items;

    public BannerWithItemsVO(List<BannerItemVO> items) {
        this.items = items;
    }
}
