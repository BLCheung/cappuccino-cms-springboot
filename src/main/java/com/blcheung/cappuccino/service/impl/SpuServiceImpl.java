package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.bo.SpuDetailBO;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.SpuDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.SpuMapper;
import com.blcheung.cappuccino.model.*;
import com.blcheung.cappuccino.service.*;
import com.blcheung.cappuccino.vo.SpuDetailVO;
import com.blcheung.cappuccino.vo.SpuVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuDO> implements SpuService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuKeyService spuKeyService;

    @Autowired
    private SpuImgService spuImgService;

    @Autowired
    private SpuDetailImgService spuDetailImgService;

    @Override
    public Boolean isSpuExist(Long spuId) {
        if (spuId == null) return false;
        return this.lambdaQuery()
                   .select(SpuDO::getId)
                   .eq(SpuDO::getId, spuId)
                   .last("limit 1")
                   .one() != null;
    }

    @Override
    public Optional<SpuDO> getSpu(Long spuId) {
        if (spuId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(SpuDO::getId, spuId)
                   .oneOpt();
    }

    @Override
    public PagingVO<SpuVO> getSpuPage(BasePagingDTO dto) {
        Page<SpuDO> pageable = PagingKit.build(dto, SpuDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, SpuVO.class);
    }

    @Override
    public SpuDetailVO getSpuDetail(Long spuId) {
        SpuDO spuDO = this.getSpu(spuId)
                          .orElseThrow(() -> new NotFoundException(10501));
        SpuDetailBO spuDetailBO = this.getBaseMapper()
                                      .getSpuDetail(spuDO);

        return BeanKit.transform(spuDO, new SpuDetailVO(spuDetailBO));
    }

    @Transactional
    @Override
    public SpuVO create(SpuDTO dto) {
        CategoryDO categoryDO = this.categoryService.getCategory(dto.getCategoryId())
                                                    .orElseThrow(() -> new NotFoundException(10401));

        SpuDO newSpuDO = BeanKit.transform(dto, new SpuDO());

        newSpuDO.setRootCategoryId(categoryDO.getParentId());

        boolean isSaved = this.save(newSpuDO);
        if (!isSaved) throw new FailedException(10502);

        this.saveSkuKeys(newSpuDO.getId(), dto.getSpuKeyIds());
        this.saveSpuImages(newSpuDO.getId(), dto.getSpuImages());
        this.saveSpuDetailImages(newSpuDO.getId(), dto.getSpuDetailImages());

        return BeanKit.transform(newSpuDO, new SpuVO());
    }

    @Transactional
    @Override
    public SpuVO update(Long spuId, SpuDTO dto) {
        SpuDO spuDO = this.getSpu(spuId)
                          .orElseThrow(() -> new NotFoundException(10501));
        CategoryDO categoryDO = this.categoryService.getCategory(dto.getCategoryId())
                                                    .orElseThrow(() -> new NotFoundException(10401));

        SpuDO newSpuDO = BeanKit.transform(dto, spuDO);
        if (categoryDO.getParentId() != null) newSpuDO.setRootCategoryId(categoryDO.getParentId());

        boolean isUpdated = this.updateById(newSpuDO);
        if (!isUpdated) throw new FailedException(10503);

        this.updateSpuKeys(newSpuDO.getId(), dto.getSpuKeyIds());
        this.updateSpuImages(newSpuDO.getId(), dto.getSpuImages());
        this.updateSpuDetailImages(newSpuDO.getId(), dto.getSpuDetailImages());

        return BeanKit.transform(newSpuDO, new SpuVO());
    }

    @Transactional
    @Override
    public Boolean delete(Long spuId) {
        Boolean exist = this.isSpuExist(spuId);
        if (!exist) throw new NotFoundException(10501);

        return this.removeById(spuId);
    }

    private void saveSkuKeys(Long spuId, List<Long> spuKeyIds) {
        if (ObjectUtils.isEmpty(spuKeyIds) || spuId == null) return;
        List<SpuKeyDO> spuKeyRelations = spuKeyIds.stream()
                                                  .map(spuKeyId -> SpuKeyDO.builder()
                                                                           .specKeyId(spuKeyId)
                                                                           .spuId(spuId)
                                                                           .build())
                                                  .collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyRelations);
    }

    private void saveSpuImages(Long spuId, List<String> spuImgs) {
        if (ObjectUtils.isEmpty(spuImgs) || spuId == null) return;
        List<SpuImgDO> spuImgRelations = spuImgs.stream()
                                                .map(img -> new SpuImgDO(img, spuId))
                                                .collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgRelations);
    }

    private void saveSpuDetailImages(Long spuId, List<String> spuDetailImgs) {
        if (ObjectUtils.isEmpty(spuDetailImgs) || spuId == null) return;

        List<SpuDetailImgDO> spuDetailImgRelations = new ArrayList<>();

        for (int i = 0; i < spuDetailImgs.size(); i++) {
            SpuDetailImgDO spuDetailImgDO = SpuDetailImgDO.builder()
                                                          .img(spuDetailImgs.get(i))
                                                          .spuId(spuId)
                                                          .index(i)
                                                          .build();
            spuDetailImgRelations.add(spuDetailImgDO);
        }

        this.spuDetailImgService.saveBatch(spuDetailImgRelations);
    }

    private void updateSpuKeys(Long spuId, List<Long> newSpuKeyIds) {
        if (spuId == null) return;

        this.spuKeyService.remove(Wrappers.<SpuKeyDO>lambdaQuery().eq(SpuKeyDO::getSpuId, spuId));

        if (!newSpuKeyIds.isEmpty()) {
            List<SpuKeyDO> spuKeyRelations = newSpuKeyIds.stream()
                                                         .map(spuKeyId -> SpuKeyDO.builder()
                                                                                  .specKeyId(spuKeyId)
                                                                                  .spuId(spuId)
                                                                                  .build())
                                                         .collect(Collectors.toList());
            this.spuKeyService.saveBatch(spuKeyRelations);
        }
    }

    private void updateSpuImages(Long spuId, List<String> newSpuImgs) {
        if (spuId == null) return;

        this.spuImgService.remove(Wrappers.<SpuImgDO>lambdaQuery().eq(SpuImgDO::getSpuId, spuId));

        if (!newSpuImgs.isEmpty()) {
            List<SpuImgDO> spuImgRelations = newSpuImgs.stream()
                                                       .map(newSpuImg -> new SpuImgDO(newSpuImg, spuId))
                                                       .collect(Collectors.toList());
            this.spuImgService.saveBatch(spuImgRelations);
        }
    }

    private void updateSpuDetailImages(Long spuId, List<String> newSpuDetailImgs) {
        if (spuId == null) return;

        this.spuDetailImgService.remove(Wrappers.<SpuDetailImgDO>lambdaQuery().eq(SpuDetailImgDO::getSpuId, spuId));

        if (!newSpuDetailImgs.isEmpty()) {
            List<SpuDetailImgDO> spuDetailImgRelations = new ArrayList<>();
            for (int i = 0; i < newSpuDetailImgs.size(); i++) {
                SpuDetailImgDO detailImgDO = new SpuDetailImgDO(newSpuDetailImgs.get(i), spuId, i);
                spuDetailImgRelations.add(detailImgDO);
            }
            this.spuDetailImgService.saveBatch(spuDetailImgRelations);
        }
    }
}
