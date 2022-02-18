package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.mapper.SkuSpecMapper;
import com.blcheung.zblmissyouadmin.model.SkuDO;
import com.blcheung.zblmissyouadmin.model.SkuSpecDO;
import com.blcheung.zblmissyouadmin.model.SpecKeyValueDO;
import com.blcheung.zblmissyouadmin.service.SkuSpecService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-18
 */
@Service
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpecDO> implements SkuSpecService {

    @Override
    public Boolean saveSkuSpecRelations(SkuDO skuDO, List<SpecKeyValueDO> specKeyValues) {
        if (specKeyValues.isEmpty()) return true;
        List<SkuSpecDO> skuSpecRelations = specKeyValues.stream()
                                                        .map(kv -> SkuSpecDO.builder()
                                                                            .spuId(skuDO.getSpuId())
                                                                            .skuId(skuDO.getId())
                                                                            .keyId(kv.getKeyId())
                                                                            .valueId(kv.getValueId())
                                                                            .build())
                                                        .collect(Collectors.toList());
        return this.saveBatch(skuSpecRelations);
    }

    @Override
    public Boolean deleteBySkuId(Long skuId) {
        SkuSpecDO skuSpecDO = this.lambdaQuery()
                                  .select(SkuSpecDO::getId)
                                  .eq(SkuSpecDO::getSkuId, skuId)
                                  .last("limit 1")
                                  .one();
        if (skuSpecDO == null) return true;
        return this.remove(Wrappers.<SkuSpecDO>lambdaQuery().eq(SkuSpecDO::getSkuId, skuId));
    }
}
