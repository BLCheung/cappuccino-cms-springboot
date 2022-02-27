package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.util.JSONConverterUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author BLCheung
 * @date 2022/2/28 1:19 上午
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderDetailVO extends OrderVO {

    private List<Map<String, Object>> snapItems;

    private String snapAddress;

    /**
     * 当前订单所使用的优惠券
     */
    private List<CouponVO> couponList;

    public OrderDetailVO(List<CouponVO> couponList) {
        this.couponList = couponList;
    }

    public void setSnapItems(String snapItems) {
        this.snapItems = JSONConverterUtil.convertJSONToObject(snapItems,
                                                               new TypeReference<List<Map<String, Object>>>() {});
    }
}
