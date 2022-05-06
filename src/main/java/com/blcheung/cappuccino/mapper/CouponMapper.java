package com.blcheung.cappuccino.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.cappuccino.model.CouponDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-21
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    /**
     * 通过活动id获取所有优惠券
     *
     * @param activityId
     * @return java.util.List<com.blcheung.cappuccino.model.CouponDO>
     * @author BLCheung
     * @date 2022/2/23 1:05 上午
     */
    List<CouponDO> getCouponsByActivityId(@Param("activityId") Long activityId);

    /**
     * 获取优惠券分页
     *
     * @param page
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.blcheung.cappuccino.model.CouponDO>
     * @author BLCheung
     * @date 2022/2/28 3:04 上午
     */
    Page<CouponDO> getCouponPage(Page<CouponDO> page);

    /**
     * 获取用户订单的优惠券
     *
     * @param orderId
     * @param userId
     * @return java.util.List<com.blcheung.cappuccino.model.CouponDO>
     * @author BLCheung
     * @date 2022/2/28 3:04 上午
     */
    List<CouponDO> getCouponsByOrderIdAndUserId(@Param("orderId") Long orderId, @Param("userId") Long userId);
}
