package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.common.constant.Macro;
import com.blcheung.zblmissyouadmin.common.enumeration.CouponCondition;
import com.blcheung.zblmissyouadmin.common.enumeration.CouponType;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.ParameterException;
import com.blcheung.zblmissyouadmin.dto.CouponDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.model.CategoryDO;
import com.blcheung.zblmissyouadmin.model.CouponDO;
import com.blcheung.zblmissyouadmin.mapper.CouponMapper;
import com.blcheung.zblmissyouadmin.service.ActivityService;
import com.blcheung.zblmissyouadmin.service.CategoryService;
import com.blcheung.zblmissyouadmin.service.CouponCategoryService;
import com.blcheung.zblmissyouadmin.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.vo.CouponDetailVO;
import com.blcheung.zblmissyouadmin.vo.CouponVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-21
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CouponCategoryService couponCategoryService;

    @Override
    public CouponDO getCoupon(Long couponId) {
        return this.lambdaQuery()
                   .eq(couponId != null, CouponDO::getId, couponId)
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10651));
    }

    @Override
    public Boolean isCouponExist(Long couponId) {
        if (couponId == null) return false;
        Long count = this.lambdaQuery()
                         .select(CouponDO::getId)
                         .eq(CouponDO::getId, couponId)
                         .last("limit 1")
                         .count();
        return count > 0;
    }

    @Override
    public PagingVO<CouponVO> getCouponPage(BasePagingDTO dto) {
        Page<CouponDO> pageable = PagingKit.buildLatest(dto, CouponDO.class);
        pageable = this.getBaseMapper()
                       .getCouponPage(pageable);

        return PagingKit.resolve(pageable, CouponVO.class);
    }

    @Override
    public CouponDetailVO getCouponDetail(Long couponId) {
        CouponDO couponDO = this.getCoupon(couponId);
        if (Macro.FAIL == couponDO.getWholeStore()) {
            List<CategoryDO> categories = this.categoryService.getCategoriesByCouponId(couponDO.getId());
            return BeanKit.transform(couponDO, new CouponDetailVO(categories));
        }
        return BeanKit.transform(couponDO, new CouponDetailVO());
    }

    @Transactional
    @Override
    public CouponVO createCoupon(CouponDTO dto) {
        this.validateCouponCondition(dto);
        this.validateCouponType(dto);

        CouponDO newCouponDO = BeanKit.transform(dto, new CouponDO());
        boolean isSaved = this.save(newCouponDO);
        if (!isSaved) throw new FailedException(10652);

        if (!dto.getCategoryIds()
                .isEmpty()) {
            boolean isRelationsSaved = this.couponCategoryService.addCouponCategoryRelations(newCouponDO.getId(),
                                                                                             dto.getCategoryIds());
            if (!isRelationsSaved) throw new FailedException(10657);
        }

        return BeanKit.transform(newCouponDO, new CouponVO());
    }

    @Transactional
    @Override
    public CouponVO updateCoupon(Long couponId, CouponDTO dto) {
        this.validateCouponCondition(dto);
        this.validateCouponType(dto);

        CouponDO couponDO = this.getCoupon(couponId);
        if (!ObjectUtils.isEmpty(dto.getCategoryIds())) {
            couponDO.setWholeStore(CouponCondition.CATEGORY.getValue());
        } else {
            couponDO.setWholeStore(CouponCondition.WHOLE_STORE.getValue());
        }

        CouponDO newCouponDO = BeanKit.transform(dto, couponDO);

        boolean isUpdated = this.updateById(newCouponDO);
        if (!isUpdated) throw new FailedException(10653);

        if (CouponCondition.CATEGORY.getValue()
                                    .equals(newCouponDO.getWholeStore())) {
            boolean isRelationsUpdated =
                    this.couponCategoryService.deleteCouponCategoryRelations(newCouponDO.getId()) &&
                    this.couponCategoryService.addCouponCategoryRelations(newCouponDO.getId(), dto.getCategoryIds());
            if (!isRelationsUpdated) throw new FailedException(10657);
        } else {
            boolean isRelationsDeleted = this.couponCategoryService.deleteCouponCategoryRelations(newCouponDO.getId());
            if (!isRelationsDeleted) throw new FailedException(10658);
        }

        return BeanKit.transform(newCouponDO, new CouponVO());
    }

    @Transactional
    @Override
    public Boolean deleteCoupon(Long couponId) {
        CouponDO couponDO = this.getCoupon(couponId);

        if (CouponCondition.CATEGORY.getValue()
                                    .equals(couponDO.getWholeStore())) {
            Boolean isRelationsDeleted = this.couponCategoryService.deleteCouponCategoryRelations(couponDO.getId());
            if (!isRelationsDeleted) throw new FailedException(10654);
        }

        return this.removeById(couponDO);
    }

    @Override
    public List<CouponVO> getCouponsByActivity(Long activityId) {
        List<CouponDO> coupons = this.getBaseMapper()
                                     .getCouponsByActivityId(activityId);
        return coupons.isEmpty() ? Collections.emptyList() : BeanKit.transformList(coupons, CouponVO.class);
    }


    private void validateCouponCondition(CouponDTO dto) {
        // '全场'与'品类'范围限制只能同时存在一种
        if (CouponCondition.WHOLE_STORE.getValue()
                                       .equals(dto.getWholeStore()) && !ObjectUtils.isEmpty(dto.getCategoryIds()))
            throw new ParameterException(10656);
    }

    private void validateCouponType(CouponDTO dto) {
        Integer type = dto.getType();
        CouponType couponType = CouponType.toType(type)
                                          .orElseThrow(() -> new ParameterException(10659));

        BigDecimal fullMoney = dto.getFullMoney();
        BigDecimal minus = dto.getMinus();
        BigDecimal rate = dto.getRate();

        switch (couponType) {
            case NO_THRESHOLD -> {
                this.validateMinusOrRate(minus);
                this.validateMinusOrRate(rate);
            }
            case FULL_MINUS -> {
                this.validateFullMoney(fullMoney);
                this.validateMinusOrRate(minus);
                this.validateMinusPrice(fullMoney, minus);
            }
            case FULL_OFF -> {
                this.validateFullMoney(fullMoney);
                this.validateMinusOrRate(rate);
            }
        }
    }

    private void validateMinusOrRate(BigDecimal minusOrRate) {
        if (minusOrRate == null) throw new ParameterException(10660);
        BigDecimal zero = new BigDecimal("0");
        if (minusOrRate.compareTo(zero) <= 0) throw new ParameterException(10660);
    }

    private void validateFullMoney(BigDecimal fullMoney) {
        BigDecimal zero = new BigDecimal("0");
        if (fullMoney == null || fullMoney.compareTo(zero) <= 0) throw new ParameterException(10661);
    }

    private void validateMinusPrice(BigDecimal fullMoney, BigDecimal minus) {
        if (fullMoney.compareTo(minus) <= 0) throw new ParameterException(10662);
    }
}
