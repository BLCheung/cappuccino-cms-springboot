package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.ForbiddenException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.ActivityCouponDTO;
import com.blcheung.cappuccino.dto.ActivityDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.ActivityMapper;
import com.blcheung.cappuccino.model.ActivityCouponDO;
import com.blcheung.cappuccino.model.ActivityDO;
import com.blcheung.cappuccino.model.CouponDO;
import com.blcheung.cappuccino.service.ActivityCouponService;
import com.blcheung.cappuccino.service.ActivityService;
import com.blcheung.cappuccino.service.CouponService;
import com.blcheung.cappuccino.vo.ActivityVO;
import com.blcheung.cappuccino.vo.CouponVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-03
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityService {

    @Autowired
    private CouponService couponService;

    @Autowired
    private ActivityCouponService activityCouponService;

    @Override
    public void checkActivityExist(Long activityId) {
        if (activityId == null) throw new NotFoundException(10351);
        this.lambdaQuery()
            .select(ActivityDO::getId)
            .eq(ActivityDO::getId, activityId)
            .last("limit 1")
            .oneOpt()
            .orElseThrow(() -> new NotFoundException(10351));
    }

    @Override
    public Optional<ActivityDO> getActivity(Long activityId) {
        if (activityId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(ActivityDO::getId, activityId)
                   .oneOpt();
    }

    @Override
    public ActivityVO getActivityDetail(Long activityId) {
        ActivityDO activityDO = this.getActivity(activityId)
                                    .orElseThrow(() -> new NotFoundException(10351));
        return BeanKit.transform(activityDO, new ActivityVO());
    }

    @Transactional
    @Override
    public ActivityVO createActivity(ActivityDTO dto) {
        ActivityDO newActivityDO = BeanKit.transform(dto, new ActivityDO());

        boolean isSave = this.save(newActivityDO);
        if (!isSave) throw new FailedException(10352);

        return BeanKit.transform(newActivityDO, new ActivityVO());
    }

    @Transactional
    @Override
    public ActivityVO updateActivity(Long activityId, ActivityDTO dto) {
        ActivityDO activityDO = this.getActivity(activityId)
                                    .orElseThrow(() -> new NotFoundException(10351));

        ActivityDO newActivityDO = BeanKit.transform(dto, activityDO);

        boolean isUpdated = this.updateById(newActivityDO);
        if (!isUpdated) throw new FailedException(10353);

        return BeanKit.transform(newActivityDO, new ActivityVO());
    }

    @Transactional
    @Override
    public Boolean deleteActivity(Long activityId) {
        this.checkActivityExist(activityId);

        Boolean isRelationExist = this.activityCouponService.isActivityCouponRelationExist(activityId);
        if (isRelationExist) throw new ForbiddenException(10359);

        return this.removeById(activityId);
    }

    @Override
    public PagingVO<ActivityVO> getActivityPage(BasePagingDTO dto) {
        Page<ActivityDO> pageable = PagingKit.build(dto, ActivityDO.class);
        pageable = this.lambdaQuery()
                       .orderByDesc(ActivityDO::getCreateTime)
                       .page(pageable);
        return PagingKit.resolve(pageable, ActivityVO.class);
    }

    @Override
    public List<CouponVO> getActivityCoupons(Long activityId) {
        this.checkActivityExist(activityId);

        return this.couponService.getCouponsByActivity(activityId);
    }

    @Transactional
    @Override
    public CouponVO activityAddCoupon(ActivityCouponDTO dto) {
        CouponDO couponDO = this.couponService.getCoupon(dto.getCouponId());

        this.checkActivityExist(dto.getActivityId());

        if (couponDO.getActivityId() != null) {
            throw new ForbiddenException(couponDO.getActivityId()
                                                 .equals(dto.getActivityId()) ? 10356 : 10355);
        }

        couponDO.setActivityId(dto.getActivityId());
        boolean isUpdated = this.couponService.updateById(couponDO);
        if (!isUpdated) throw new FailedException(10357);

        ActivityCouponDO activityCouponRelation = new ActivityCouponDO(couponDO.getId(), couponDO.getActivityId());
        boolean isSaved = this.activityCouponService.save(activityCouponRelation);
        if (!isSaved) throw new FailedException(10357);

        return BeanKit.transform(couponDO, new CouponVO());
    }

    @Transactional
    @Override
    public Boolean activityDeleteCoupon(ActivityCouponDTO dto) {
        CouponDO couponDO = this.couponService.getCoupon(dto.getCouponId());

        if (couponDO.getActivityId() == null) return true;

        this.checkActivityExist(dto.getActivityId());

        couponDO.setActivityId(null);
        return this.couponService.updateById(couponDO) &&
               this.activityCouponService.deleteRelation(dto.getActivityId(), couponDO.getId());
    }
}
