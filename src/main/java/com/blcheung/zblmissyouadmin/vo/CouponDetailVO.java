package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.model.CategoryDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/22 4:43 上午
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponDetailVO extends CouponVO {

    private List<CategoryVO> categories;

    public CouponDetailVO(List<CategoryDO> categories) {
        this.categories = BeanKit.transformList(categories, CategoryVO.class);
    }
}
