package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.ThemeDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.ThemeMapper;
import com.blcheung.zblmissyouadmin.model.ThemeDO;
import com.blcheung.zblmissyouadmin.service.ThemeService;
import com.blcheung.zblmissyouadmin.vo.ThemeVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Optional<ThemeDO> getTheme(Long themeId) {
        if (themeId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(ThemeDO::getId, themeId)
                   .oneOpt();
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
        return this.removeById(themeDO.getId());
    }

    @Override
    public PagingVO<ThemeVO> getThemePage(BasePagingDTO dto) {
        Page<ThemeDO> pageable = PagingKit.build(dto, ThemeDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, ThemeVO.class);
    }
}
