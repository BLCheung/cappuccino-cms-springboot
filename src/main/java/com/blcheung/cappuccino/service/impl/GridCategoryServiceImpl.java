package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.ForbiddenException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.GridCategoryDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.mapper.GridCategoryMapper;
import com.blcheung.cappuccino.model.CategoryDO;
import com.blcheung.cappuccino.model.GridCategoryDO;
import com.blcheung.cappuccino.service.CategoryService;
import com.blcheung.cappuccino.service.GridCategoryService;
import com.blcheung.cappuccino.vo.GridCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-06
 */
@Service
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper, GridCategoryDO>
        implements GridCategoryService {

    @Autowired
    private CategoryService categoryService;

    // 宫格最大数量
    @Value("${cappuccino.grid-category-maximum}")
    private int maximum;

    @Override
    public Optional<GridCategoryDO> getGridCategory(Long gridId) {
        if (gridId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(GridCategoryDO::getId, gridId)
                   .oneOpt();
    }

    @Transactional
    @Override
    public GridCategoryVO createGirdCategory(GridCategoryDTO dto) {
        this.validateGridMaximum();
        CategoryDO categoryDO = this.categoryService.getCategory(dto.getCategoryId())
                                                    .orElseThrow(() -> new NotFoundException(10401));
        GridCategoryDO newGridCategoryDO = this.assembleGridCategory(categoryDO, dto, new GridCategoryDO());

        boolean isSaved = this.save(newGridCategoryDO);
        if (!isSaved) throw new FailedException(10422);

        return BeanKit.transform(newGridCategoryDO, new GridCategoryVO());
    }

    @Transactional
    @Override
    public GridCategoryVO updateGridCategory(Long id, GridCategoryDTO dto) {
        GridCategoryDO gridCategoryDO = this.getGridCategory(id)
                                            .orElseThrow(() -> new NotFoundException(10421));

        CategoryDO categoryDO = this.categoryService.getCategory(dto.getCategoryId())
                                                    .orElseThrow(() -> new NotFoundException(10401));
        GridCategoryDO newGridCategoryDO = this.assembleGridCategory(categoryDO, dto, gridCategoryDO);
        boolean isUpdated = this.updateById(newGridCategoryDO);
        if (!isUpdated) throw new FailedException(10423);

        return BeanKit.transform(newGridCategoryDO, new GridCategoryVO());
    }

    @Transactional
    @Override
    public Boolean deleteGridCategory(Long id) {
        GridCategoryDO gridCategoryDO = this.getGridCategory(id)
                                            .orElseThrow(() -> new NotFoundException(10421));
        return this.removeById(gridCategoryDO);
    }

    @Override
    public List<GridCategoryVO> getAllGridCategory() {
        return this.lambdaQuery()
                   .list()
                   .stream()
                   .map(gridCategoryDO -> BeanKit.transform(gridCategoryDO, new GridCategoryVO()))
                   .collect(Collectors.toList());
    }

    private GridCategoryDO assembleGridCategory(CategoryDO categoryDO, GridCategoryDTO dto,
                                                GridCategoryDO gridCategoryDO) {
        GridCategoryDO newGridCategoryDO = BeanKit.transform(dto, gridCategoryDO);
        Long parentId = categoryDO.getParentId();
        if (!ObjectUtils.isEmpty(parentId) && !ObjectUtils.isEmpty(newGridCategoryDO)) {
            newGridCategoryDO.setRootCategoryId(parentId);
        }
        return newGridCategoryDO;
    }

    private void validateGridMaximum() {
        Long current = this.lambdaQuery()
                           .select(GridCategoryDO::getId)
                           .count();
        if (current >= this.maximum) throw new ForbiddenException(10425);
    }
}
