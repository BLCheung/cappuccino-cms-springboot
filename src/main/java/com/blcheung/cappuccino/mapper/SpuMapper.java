package com.blcheung.cappuccino.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.cappuccino.bo.SpuDetailBO;
import com.blcheung.cappuccino.model.SpuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Repository
public interface SpuMapper extends BaseMapper<SpuDO> {

    /**
     * 获取Spu业务详情
     *
     * @param spuDO
     * @return com.blcheung.cappuccino.bo.SpuDetailBO
     * @author BLCheung
     * @date 2022/2/11 12:14 上午
     */
    SpuDetailBO getSpuDetail(@Param("spu") SpuDO spuDO);

    /**
     * 根据主题id获取主题下所有Spu
     *
     * @param themeId
     * @return java.util.List<com.blcheung.cappuccino.model.SpuDO>
     * @author BLCheung
     * @date 2022/2/19 11:16 下午
     */
    List<SpuDO> getSpusByThemeId(@Param("themeId") Long themeId);
}
