package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.model.SkuDO;
import com.blcheung.cappuccino.model.SkuSpecDO;
import com.blcheung.cappuccino.model.SpecKeyValueDO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-18
 */
public interface SkuSpecService extends IService<SkuSpecDO> {

    /**
     * 保存Sku与规格的关联关系
     *
     * @param skuDO
     * @param specKeyValues
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/18 11:52 下午
     */
    Boolean saveSkuSpecRelations(SkuDO skuDO, List<SpecKeyValueDO> specKeyValues);

    /**
     * 根据SkuId删除Sku与规格的关联关系
     *
     * @param skuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/18 11:48 下午
     */
    Boolean deleteBySkuId(Long skuId);
}
