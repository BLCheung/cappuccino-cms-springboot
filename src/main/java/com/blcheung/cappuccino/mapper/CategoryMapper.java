package com.blcheung.cappuccino.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.cappuccino.model.CategoryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-04
 */
@Repository
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据id获取分类名
     *
     * @param categoryId
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/2/10 10:08 下午
     */
    String getNameById(@Param("categoryId") Long categoryId);

    /**
     * 通过优惠券id获取该优惠券下所有分类
     *
     * @param couponId
     * @return java.util.List<com.blcheung.cappuccino.model.CategoryDO>
     * @author BLCheung
     * @date 2022/2/22 4:50 上午
     */
    List<CategoryDO> getAllCategoryByCouponId(@Param("couponId") Long couponId);
}
