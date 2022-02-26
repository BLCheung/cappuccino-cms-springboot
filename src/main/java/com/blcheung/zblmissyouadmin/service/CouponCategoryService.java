package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.model.CouponCategoryDO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-22
 */
public interface CouponCategoryService extends IService<CouponCategoryDO> {

    /**
     * 保存优惠券分类关联关系
     *
     * @param couponId
     * @param addCategoryIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/24 12:07 上午
     */
    Boolean addCouponCategoryRelations(Long couponId, List<Long> addCategoryIds);

    /**
     * 删除优惠券分类关联关系
     *
     * @param couponId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/24 12:08 上午
     */
    Boolean deleteCouponCategoryRelations(Long couponId);
}
