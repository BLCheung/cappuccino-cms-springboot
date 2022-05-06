package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.ForbiddenException;
import com.blcheung.cappuccino.mapper.ThemeSpuMapper;
import com.blcheung.cappuccino.model.ThemeSpuDO;
import com.blcheung.cappuccino.service.ThemeSpuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-19
 */
@Service
public class ThemeSpuServiceImpl extends ServiceImpl<ThemeSpuMapper, ThemeSpuDO> implements ThemeSpuService {

    @Override
    public Boolean isThemeHasSpu(Long themeId) {
        if (themeId == null) throw new FailedException(10450);
        return this.lambdaQuery()
                   .select(ThemeSpuDO::getId)
                   .eq(ThemeSpuDO::getThemeId, themeId)
                   .last("limit 1")
                   .count() > 0;
    }

    @Override
    public Boolean isThemeSpuRelationExist(Long themeId, Long spuId) {
        if (themeId == null || spuId == null) throw new FailedException(10450);
        Long count = this.lambdaQuery()
                         .select(ThemeSpuDO::getId)
                         .eq(ThemeSpuDO::getThemeId, themeId)
                         .eq(ThemeSpuDO::getSpuId, spuId)
                         .last("limit 1")
                         .count();
        return count > 0;
    }

    @Override
    public Boolean addThemeSpuRelation(Long themeId, Long spuId) {
        if (themeId == null || spuId == null) return false;

        Boolean isRelationExist = this.isThemeSpuRelationExist(themeId, spuId);
        if (isRelationExist) throw new ForbiddenException(10455);

        ThemeSpuDO relation = ThemeSpuDO.builder()
                                        .themeId(themeId)
                                        .spuId(spuId)
                                        .build();
        return this.save(relation);
    }

    @Override
    public Boolean deleteThemeSpuRelation(Long themeId, Long spuId) {
        if (themeId == null || spuId == null) return true;
        return this.remove(Wrappers.<ThemeSpuDO>lambdaQuery().eq(ThemeSpuDO::getThemeId, themeId)
                                                             .eq(ThemeSpuDO::getSpuId, spuId));
    }
}
