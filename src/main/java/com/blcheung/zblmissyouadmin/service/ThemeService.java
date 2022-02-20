package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.ThemeDTO;
import com.blcheung.zblmissyouadmin.dto.ThemeSpuDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.ThemeDO;
import com.blcheung.zblmissyouadmin.vo.SpuVO;
import com.blcheung.zblmissyouadmin.vo.ThemeVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-07
 */
public interface ThemeService extends IService<ThemeDO> {

    /**
     * 获取主题
     *
     * @param themeId
     * @return java.util.Optional<com.blcheung.zblmissyouadmin.model.ThemeDO>
     * @author BLCheung
     * @date 2022/2/8 9:47 下午
     */
    Optional<ThemeDO> getTheme(Long themeId);

    /**
     * 获取主题详情
     *
     * @param themeId
     * @return com.blcheung.zblmissyouadmin.vo.ThemeVO
     * @author BLCheung
     * @date 2022/2/20 11:40 下午
     */
    ThemeVO getThemeDetail(Long themeId);

    /**
     * 检查主题是否存在
     *
     * @param themeId
     * @author BLCheung
     * @date 2022/2/19 10:24 下午
     */
    void checkThemeExist(Long themeId);

    /**
     * 创建主题
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.ThemeVO
     * @author BLCheung
     * @date 2022/2/8 9:13 下午
     */
    ThemeVO createTheme(ThemeDTO dto);

    /**
     * 更新主题
     *
     * @param themeId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.ThemeVO
     * @author BLCheung
     * @date 2022/2/8 9:16 下午
     */
    ThemeVO updateTheme(Long themeId, ThemeDTO dto);

    /**
     * 删除主题
     *
     * @param themeId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/8 9:17 下午
     */
    Boolean deleteTheme(Long themeId);

    /**
     * 获取所有主题分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.ThemeVO>
     * @author BLCheung
     * @date 2022/2/8 10:13 下午
     */
    PagingVO<ThemeVO> getThemePage(BasePagingDTO dto);

    /**
     * 获取主题下所有Spu
     *
     * @param themeId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.SpuVO>
     * @author BLCheung
     * @date 2022/2/19 10:49 下午
     */
    List<SpuVO> getThemeSpus(Long themeId);

    /**
     * 往主题下添加Spu
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpuVO
     * @author BLCheung
     * @date 2022/2/19 10:22 下午
     */
    SpuVO addThemeSpu(ThemeSpuDTO dto);

    /**
     * 从主题下删除Spu
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/21 1:19 上午
     */
    Boolean deleteThemeSpu(ThemeSpuDTO dto);
}
