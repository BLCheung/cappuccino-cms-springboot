package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.model.ThemeSpuDO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-19
 */
public interface ThemeSpuService extends IService<ThemeSpuDO> {

    /**
     * 检查主体下是否还存在关联的Spu
     *
     * @param themeId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/20 11:47 下午
     */
    Boolean isThemeHasSpu(Long themeId);

    /**
     * 主题与Spu的关联关系是否已存在
     *
     * @param themeId
     * @param spuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/19 11:03 下午
     */
    Boolean isThemeSpuRelationExist(Long themeId, Long spuId);

    /**
     * 保存主题与Spu的关联关系
     *
     * @param themeId
     * @param spuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/19 10:35 下午
     */
    Boolean addThemeSpuRelation(Long themeId, Long spuId);

    /**
     * 删除主题与Spu的关联关系
     *
     * @param themeId
     * @param spuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/20 11:52 下午
     */
    Boolean deleteThemeSpuRelation(Long themeId, Long spuId);
}
