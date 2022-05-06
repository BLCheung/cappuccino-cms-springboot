package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blcheung.cappuccino.model.CouponCategoryDO;
import com.blcheung.cappuccino.mapper.CouponCategoryMapper;
import com.blcheung.cappuccino.service.CouponCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-22
 */
@Service
public class CouponCategoryServiceImpl extends ServiceImpl<CouponCategoryMapper, CouponCategoryDO>
        implements CouponCategoryService {

    @Override
    public Boolean addCouponCategoryRelations(Long couponId, List<Long> addCategoryIds) {
        if (addCategoryIds.isEmpty()) return true;
        List<CouponCategoryDO> relations = addCategoryIds.stream()
                                                         .map(cId -> new CouponCategoryDO(cId, couponId))
                                                         .collect(Collectors.toList());
        return this.saveBatch(relations);
    }

    @Override
    public Boolean deleteCouponCategoryRelations(Long couponId) {
        Long count = this.lambdaQuery()
                         .select(CouponCategoryDO::getId)
                         .eq(CouponCategoryDO::getCouponId, couponId)
                         .last("limit 1")
                         .count();

        if (count <= 0) return true;

        return this.remove(Wrappers.<CouponCategoryDO>lambdaQuery().eq(CouponCategoryDO::getCouponId, couponId));
    }
}
