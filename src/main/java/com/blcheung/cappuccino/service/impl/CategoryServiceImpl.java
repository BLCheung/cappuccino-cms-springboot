package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.enumeration.RootStatus;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.ForbiddenException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.CategoryDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.CategoryMapper;
import com.blcheung.cappuccino.model.CategoryDO;
import com.blcheung.cappuccino.service.CategoryService;
import com.blcheung.cappuccino.vo.CategoryVO;
import com.blcheung.cappuccino.vo.RootCategoryVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Override
    public Optional<CategoryDO> getCategory(Long categoryId) {
        if (categoryId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(CategoryDO::getId, categoryId)
                   .oneOpt();
    }

    @Override
    public List<RootCategoryVO> getAllCategoryTree() {
        List<CategoryDO> all = this.lambdaQuery()
                                   .list();
        List<CategoryDO> roots = all.stream()
                                    .filter(categoryDO -> categoryDO.getParentId() == null)
                                    .collect(Collectors.toList());
        List<CategoryDO> subs = all.stream()
                                   .filter(categoryDO -> categoryDO.getParentId() != null)
                                   .collect(Collectors.toList());

        List<RootCategoryVO> rootResults = new ArrayList<>(roots.size());
        for (CategoryDO root : roots) {
            List<CategoryVO> rootSubs = new ArrayList<>();
            for (CategoryDO sub : subs) {
                if (sub.getParentId()
                       .equals(root.getId())) rootSubs.add(BeanKit.transform(sub, new CategoryVO()));
            }
            rootResults.add(BeanKit.transform(root, new RootCategoryVO(rootSubs)));
        }

        rootResults.sort((c1, c2) -> {
            if (c1.getIndex() == null || c2.getIndex() == null) return 0;
            return c1.getIndex() - c2.getIndex();
        });

        return rootResults;
    }

    @Transactional
    @Override
    public CategoryVO createCategory(CategoryDTO dto) {
        CategoryDO newCategoryDO = BeanKit.transform(dto, new CategoryDO());

        boolean isSave = this.save(newCategoryDO);
        if (!isSave) throw new FailedException(10402);

        return BeanKit.transform(newCategoryDO, new CategoryVO());
    }

    @Transactional
    @Override
    public CategoryVO updateCategory(Long categoryId, CategoryDTO dto) {
        CategoryDO categoryDO = this.getCategory(categoryId)
                                    .orElseThrow(() -> new NotFoundException(10401));

        CategoryDO newCategoryDO = BeanKit.transform(dto, categoryDO);
        boolean isUpdated = this.updateById(newCategoryDO);
        if (!isUpdated) throw new FailedException(10403);

        return BeanKit.transform(newCategoryDO, new CategoryVO());
    }

    @Transactional
    @Override
    public Boolean deleteCategory(Long categoryId) {
        CategoryDO categoryDO = this.getCategory(categoryId)
                                    .orElseThrow(() -> new NotFoundException(10401));
        // 是否为父分类
        if (categoryDO.getIsRoot()
                      .equals(RootStatus.ROOT.getValue())) {
            // 是否还存在子分类
            Boolean hasSubCategory = this.hasSubCategory(categoryId);
            if (hasSubCategory) throw new ForbiddenException(10405);
        }

        return this.removeById(categoryDO.getId());
    }

    @Override
    public PagingVO<CategoryVO> getCategoryPage(BasePagingDTO dto) {
        Page<CategoryDO> pageable = PagingKit.build(dto, CategoryDO.class);
        pageable = this.lambdaQuery()
                       .eq(CategoryDO::getIsRoot, RootStatus.ROOT.getValue())
                       .page(pageable);
        return PagingKit.resolve(pageable, CategoryVO.class);
    }

    @Override
    public PagingVO<CategoryVO> getSubCategoryPage(Long parentId, BasePagingDTO dto) {
        CategoryDO categoryDO = this.getCategory(parentId)
                                    .orElseThrow(() -> new NotFoundException(10401));

        Page<CategoryDO> pageable = PagingKit.build(dto, CategoryDO.class);
        pageable = this.lambdaQuery()
                       .eq(CategoryDO::getParentId, categoryDO.getId())
                       .eq(CategoryDO::getIsRoot, RootStatus.NOT_ROOT.getValue())
                       .page(pageable);

        return PagingKit.resolve(pageable, CategoryVO.class);
    }

    @Override
    public Boolean hasSubCategory(Long parentId) {
        if (parentId == null) return false;
        return this.lambdaQuery()
                   .select(CategoryDO::getId)
                   .eq(CategoryDO::getParentId, parentId)
                   .eq(CategoryDO::getIsRoot, RootStatus.NOT_ROOT.getValue())
                   .last("limit 1")
                   .one() != null;
    }

    @Override
    public List<CategoryDO> getCategoriesByCouponId(Long couponId) {
        List<CategoryDO> categories = this.getBaseMapper()
                                          .getAllCategoryByCouponId(couponId);
        return categories.isEmpty() ? Collections.emptyList() : categories;
    }
}
