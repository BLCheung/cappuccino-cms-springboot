package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.CouponDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.CouponDO;
import com.blcheung.zblmissyouadmin.vo.CouponDetailVO;
import com.blcheung.zblmissyouadmin.vo.CouponVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-21
 */
public interface CouponService extends IService<CouponDO> {

    /**
     * 获取优惠券
     *
     * @param couponId
     * @return com.blcheung.zblmissyouadmin.model.CouponDO
     * @author BLCheung
     * @date 2022/2/22 4:32 上午
     */
    CouponDO getCoupon(Long couponId);

    /**
     * 优惠券是否存在
     *
     * @param couponId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/23 12:38 上午
     */
    Boolean isCouponExist(Long couponId);

    /**
     * 获取优惠券分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.CouponVO>
     * @author BLCheung
     * @date 2022/2/22 4:19 上午
     */
    PagingVO<CouponVO> getCouponPage(BasePagingDTO dto);

    /**
     * 获取优惠券详情
     *
     * @param couponId
     * @return com.blcheung.zblmissyouadmin.vo.CouponDetailVO
     * @author BLCheung
     * @date 2022/2/22 4:37 上午
     */
    CouponDetailVO getCouponDetail(Long couponId);

    /**
     * 创建优惠券
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.CouponVO
     * @author BLCheung
     * @date 2022/2/22 5:11 上午
     */
    CouponVO createCoupon(CouponDTO dto);

    /**
     * 更新优惠券
     *
     * @param couponId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.CouponVO
     * @author BLCheung
     * @date 2022/2/24 12:00 上午
     */
    CouponVO updateCoupon(Long couponId, CouponDTO dto);

    /**
     * 删除优惠券
     *
     * @param couponId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/24 1:04 上午
     */
    Boolean deleteCoupon(Long couponId);

    /**
     * 获取某个活动下的所有优惠券
     *
     * @param activityId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.CouponVO>
     * @author BLCheung
     * @date 2022/2/23 1:04 上午
     */
    List<CouponVO> getCouponsByActivity(Long activityId);

    /**
     * 根据用户的订单获取优惠券
     *
     * @param orderId
     * @param userId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.CouponVO>
     * @author BLCheung
     * @date 2022/2/28 3:02 上午
     */
    List<CouponVO> getCouponsByOrderAndUser(Long orderId, Long userId);
}
