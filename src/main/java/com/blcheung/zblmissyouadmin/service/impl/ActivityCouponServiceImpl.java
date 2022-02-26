package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.mapper.ActivityCouponMapper;
import com.blcheung.zblmissyouadmin.model.ActivityCouponDO;
import com.blcheung.zblmissyouadmin.service.ActivityCouponService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-22
 */
@Service
public class ActivityCouponServiceImpl extends ServiceImpl<ActivityCouponMapper, ActivityCouponDO>
        implements ActivityCouponService {

    @Override
    public Boolean deleteRelation(Long activityId, Long couponId) {
        if (activityId == null || couponId == null) return true;
        return this.remove(Wrappers.<ActivityCouponDO>lambdaQuery().eq(ActivityCouponDO::getActivityId, activityId)
                                                                   .eq(ActivityCouponDO::getCouponId, couponId));
    }

    @Override
    public Boolean isActivityCouponRelationExist(Long activityId) {
        Long count = this.lambdaQuery()
                         .select(ActivityCouponDO::getId)
                         .eq(ActivityCouponDO::getActivityId, activityId)
                         .last("limit 1")
                         .count();
        return count > 0;
    }
}
