package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.configuration.SkuProperties;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.SkuDTO;
import com.blcheung.zblmissyouadmin.dto.SpecSelectorDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.SkuMapper;
import com.blcheung.zblmissyouadmin.model.SkuDO;
import com.blcheung.zblmissyouadmin.model.SpecKeyValueDO;
import com.blcheung.zblmissyouadmin.model.SpuDO;
import com.blcheung.zblmissyouadmin.service.SkuService;
import com.blcheung.zblmissyouadmin.service.SkuSpecService;
import com.blcheung.zblmissyouadmin.service.SpecKeyService;
import com.blcheung.zblmissyouadmin.service.SpuService;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import com.blcheung.zblmissyouadmin.vo.SkuVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuDO> implements SkuService {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SpecKeyService specKeyService;

    @Autowired
    private SkuSpecService skuSpecService;

    @Autowired
    private SkuProperties skuProperties;

    @Override
    public SkuDO getSku(Long skuId) {
        return this.lambdaQuery()
                   .eq(skuId != null, SkuDO::getId, skuId)
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10551));
    }

    @Override
    public void checkSkuTitleExist(String skuTitle) {
        this.lambdaQuery()
            .select(SkuDO::getId)
            .eq(!StringUtils.isEmpty(skuTitle), SkuDO::getTitle, skuTitle)
            .last("limit 1")
            .oneOpt()
            .ifPresent(skuDO -> { throw new ForbiddenException(10555); });
    }

    @Override
    public PagingVO<SkuVO> getSkuPage(BasePagingDTO dto) {
        Page<SkuDO> pageable = PagingKit.build(dto, SkuDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, new SkuVO());
    }

    @Transactional
    @Override
    public SkuVO create(SkuDTO dto) {
        this.checkSkuTitleExist(dto.getTitle());

        SpuDO spuDO = this.spuService.getSpu(dto.getSpuId())
                                     .orElseThrow(() -> new NotFoundException(10501));

        // 校验当前Sku所选规格是否出现重复的规格名或规格值
        this.checkIsDistinctSpecs(dto.getSpecs());

        List<SpecKeyValueDO> specKeyValues = this.specKeyService.getSpecValues(dto.getSpecs());
        if (specKeyValues == null) throw new ForbiddenException(10556);

        SkuDO newSkuDO = BeanKit.transform(dto, new SkuDO());
        this.setSkuSpecAndCode(newSkuDO, specKeyValues);
        this.setSkuLimitBuyCount(newSkuDO);
        this.setSkuCategory(newSkuDO, spuDO);

        // 检查所属的Spu下是否已存在相同的Sku规格
        this.checkSkuCodeByCreate(newSkuDO);

        boolean isSaved = this.save(newSkuDO);
        if (!isSaved) throw new FailedException(10552);

        Boolean isRelationsSaved = this.skuSpecService.saveSkuSpecRelations(newSkuDO, specKeyValues);
        if (!isRelationsSaved) throw new FailedException(10550);

        return BeanKit.transform(newSkuDO, new SkuVO());
    }

    @Override
    public SkuVO update(Long skuId, SkuDTO dto) {
        SkuDO skuDO = this.getSku(skuId);

        SpuDO spuDO = null;
        if (!dto.getSpuId()
                .equals(skuDO.getSpuId())) {
            spuDO = this.spuService.getSpu(dto.getSpuId())
                                   .orElseThrow(() -> new NotFoundException(10501));
        }

        // 校验当前Sku所选规格是否出现重复的规格名或规格值
        this.checkIsDistinctSpecs(dto.getSpecs());

        List<SpecKeyValueDO> specKeyValues = this.specKeyService.getSpecValues(dto.getSpecs());
        if (specKeyValues == null) throw new ForbiddenException(10556);

        SkuDO newSkuDO = BeanKit.transform(dto, skuDO);
        this.setSkuSpecAndCode(newSkuDO, specKeyValues);
        this.setSkuLimitBuyCount(newSkuDO);
        this.setSkuCategory(newSkuDO, spuDO);

        // 检查所属的Spu下是否已存在其它相同的Sku规格
        this.checkSkuCodeByUpdate(newSkuDO);

        boolean isUpdated = this.updateById(newSkuDO);
        if (!isUpdated) throw new FailedException(10553);

        boolean isRelationsUpdated = this.skuSpecService.deleteBySkuId(newSkuDO.getId()) &&
                                     this.skuSpecService.saveSkuSpecRelations(newSkuDO, specKeyValues);
        if (!isRelationsUpdated) throw new FailedException(10553);

        return BeanKit.transform(newSkuDO, new SkuVO());
    }

    @Override
    public Boolean delete(Long skuId) {
        SkuDO skuDO = this.getSku(skuId);

        boolean isDeleted = this.removeById(skuDO.getId());
        Boolean isRelationsDeleted = this.skuSpecService.deleteBySkuId(skuId);

        return isDeleted && isRelationsDeleted;
    }


    private void checkIsDistinctSpecs(List<SpecSelectorDTO> specValues) {
        if (specValues.isEmpty()) return;
        List<Long> keyIds = specValues.stream()
                                      .map(SpecSelectorDTO::getKeyId)
                                      .collect(Collectors.toList());
        List<Long> valueIds = specValues.stream()
                                        .map(SpecSelectorDTO::getValueId)
                                        .collect(Collectors.toList());
        if (!CommonUtil.isDistinctIds(keyIds) || !CommonUtil.isDistinctIds(valueIds))
            throw new ForbiddenException(10557);
    }

    private void setSkuLimitBuyCount(SkuDO skuDO) {
        if (ObjectUtils.isEmpty(skuDO.getLimitBuyCount())) skuDO.setLimitBuyCount(this.skuProperties.getMaxBuyCount());
    }

    private void setSkuCategory(SkuDO skuDO, SpuDO spuDO) {
        if (spuDO == null) return;
        skuDO.setCategoryId(spuDO.getCategoryId());
        skuDO.setRootCategoryId(spuDO.getRootCategoryId());
    }

    private void setSkuSpecAndCode(SkuDO skuDO, List<SpecKeyValueDO> specKeyValues) {
        /**
         * 按照规格名id升序排序
         * 排序函数如果返回<0的值，则kv1排在kv2前面
         * 排序函数如果返回>0的值，则kv2排在kv1前面
         * 排序函数如果返回>0，则顺序不变
         * kv1 < kv2 = <0 -> kv1, kv2
         * kv1 > kv2 = >0 -> kv2, kv1
         * kv1 = kv2 = 0  -> kv1, kv2
         */
        specKeyValues.sort((kv1, kv2) -> Math.toIntExact(kv1.getKeyId() - kv2.getKeyId()));

        Long spuId = skuDO.getSpuId();
        StringBuilder builder = new StringBuilder();
        builder.append(spuId)
               .append("$");

        int maxSize = specKeyValues.size();
        SpecKeyValueDO keyValueDO;
        for (int i = 0; i < maxSize; i++) {
            keyValueDO = specKeyValues.get(i);
            builder.append(keyValueDO.getKeyId())
                   .append("-")
                   .append(keyValueDO.getValueId());
            // 插#插到倒数第二个元素即止
            if (i < maxSize - 1) builder.append("#");
        }
        String skuCode = builder.toString();
        skuDO.setCode(skuCode);
        skuDO.setSpecs(specKeyValues);
    }

    private void checkSkuCodeByCreate(SkuDO skuDO) {
        this.lambdaQuery()
            .select(SkuDO::getId)
            .eq(SkuDO::getSpuId, skuDO.getSpuId())
            .eq(SkuDO::getCode, skuDO.getCode())
            .last("limit 1")
            .oneOpt()
            .ifPresent(s -> { throw new ForbiddenException(10558); });
    }

    private void checkSkuCodeByUpdate(SkuDO skuDO) {
        this.lambdaQuery()
            .select(SkuDO::getId)
            .eq(SkuDO::getSpuId, skuDO.getSpuId())
            .eq(SkuDO::getCode, skuDO.getCode())
            .ne(SkuDO::getId, skuDO.getId())
            .last("limit 1")
            .oneOpt()
            .ifPresent(s -> { throw new ForbiddenException(10559); });
    }
}
