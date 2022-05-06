package com.blcheung.cappuccino.mapper;

import com.blcheung.cappuccino.model.SkuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Repository
public interface SkuMapper extends BaseMapper<SkuDO> {

    /**
     * 根据id获取Sku的名称
     *
     * @param skuId
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/2/10 10:09 下午
     */
    String getSkuTitleById(@Param("skuId") Long skuId);
}
