package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.ForbiddenException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.ThemeDTO;
import com.blcheung.cappuccino.dto.ThemeSpuDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.SpuMapper;
import com.blcheung.cappuccino.mapper.ThemeMapper;
import com.blcheung.cappuccino.model.SpuDO;
import com.blcheung.cappuccino.model.ThemeDO;
import com.blcheung.cappuccino.service.SpuService;
import com.blcheung.cappuccino.service.ThemeService;
import com.blcheung.cappuccino.service.ThemeSpuService;
import com.blcheung.cappuccino.vo.SpuVO;
import com.blcheung.cappuccino.vo.ThemeVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-07
 */
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, ThemeDO> implements ThemeService {

    @Autowired
    private SpuService spuService;

    @Autowired
    private ThemeSpuService themeSpuService;

    @Autowired
    private SpuMapper spuMapper;

    @Override
    public Optional<ThemeDO> getTheme(Long themeId) {
        if (themeId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(ThemeDO::getId, themeId)
                   .oneOpt();
    }

    @Override
    public ThemeVO getThemeDetail(Long themeId) {
        ThemeDO themeDO = this.getTheme(themeId)
                              .orElseThrow(() -> new NotFoundException(10451));
        return BeanKit.transform(themeDO, new ThemeVO());
    }

    @Override
    public void checkThemeExist(Long themeId) {
        Long count = this.lambdaQuery()
                         .select(ThemeDO::getId)
                         .eq(themeId != null, ThemeDO::getId, themeId)
                         .last("limit 1")
                         .count();
        if (count <= 0) throw new NotFoundException(10451);
    }


    @Transactional
    @Override
    public ThemeVO createTheme(ThemeDTO dto) {
        ThemeDO newThemeDO = BeanKit.transform(dto, new ThemeDO());

        boolean isSaved = this.save(newThemeDO);
        if (!isSaved) throw new FailedException(10452);

        return BeanKit.transform(newThemeDO, new ThemeVO());
    }

    @Transactional
    @Override
    public ThemeVO updateTheme(Long themeId, ThemeDTO dto) {
        ThemeDO themeDO = this.getTheme(themeId)
                              .orElseThrow(() -> new NotFoundException(10451));

        ThemeDO newThemeDO = BeanKit.transform(dto, themeDO);
        boolean isUpdated = this.updateById(newThemeDO);
        if (!isUpdated) throw new FailedException(10453);

        return BeanKit.transform(newThemeDO, new ThemeVO());
    }

    @Transactional
    @Override
    public Boolean deleteTheme(Long themeId) {
        ThemeDO themeDO = this.getTheme(themeId)
                              .orElseThrow(() -> new NotFoundException(10451));

        Boolean hasSpu = this.themeSpuService.isThemeHasSpu(themeId);
        if (hasSpu) throw new ForbiddenException(10457);

        return this.removeById(themeDO.getId());
    }

    @Override
    public PagingVO<ThemeVO> getThemePage(BasePagingDTO dto) {
        Page<ThemeDO> pageable = PagingKit.build(dto, ThemeDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, ThemeVO.class);
    }

    @Override
    public List<SpuVO> getThemeSpus(Long themeId) {
        this.checkThemeExist(themeId);
        List<SpuDO> themeSpus = this.spuMapper.getSpusByThemeId(themeId);
        return BeanKit.transformList(themeSpus, SpuVO.class);
    }

    @Transactional
    @Override
    public SpuVO addThemeSpu(ThemeSpuDTO dto) {
        this.checkThemeExist(dto.getThemeId());
        SpuDO spuDO = this.spuService.getSpu(dto.getSpuId())
                                     .orElseThrow(() -> new NotFoundException(10501));

        Boolean isRelationSaved = this.themeSpuService.addThemeSpuRelation(dto.getThemeId(), spuDO.getId());
        if (!isRelationSaved) throw new FailedException(10456);

        return BeanKit.transform(spuDO, new SpuVO());
    }

    @Transactional
    @Override
    public Boolean deleteThemeSpu(ThemeSpuDTO dto) {
        Boolean isRelationExist = this.themeSpuService.isThemeSpuRelationExist(dto.getThemeId(), dto.getSpuId());
        if (!isRelationExist) throw new NotFoundException(10458);
        return this.themeSpuService.deleteThemeSpuRelation(dto.getThemeId(), dto.getSpuId());
    }
}
