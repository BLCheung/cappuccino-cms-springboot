package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.model.ActivityCouponDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-22
 */
public interface ActivityCouponService extends IService<ActivityCouponDO> {

    /**
     * 删除活动与优惠券的关联关系
     *
     * @param activityId
     * @param couponId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/23 12:28 上午
     */
    Boolean deleteRelation(Long activityId, Long couponId);

    /**
     * 活动与优惠券的关联关系是否存在
     *
     * @param activityId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/23 12:59 上午
     */
    Boolean isActivityCouponRelationExist(Long activityId);
}
