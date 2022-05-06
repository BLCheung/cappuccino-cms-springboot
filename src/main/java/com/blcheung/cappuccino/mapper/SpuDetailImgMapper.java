package com.blcheung.cappuccino.mapper;

import com.blcheung.cappuccino.model.SpuDetailImgDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
public interface SpuDetailImgMapper extends BaseMapper<SpuDetailImgDO> {

    /**
     * 获取spu的详情图
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author BLCheung
     * @date 2022/2/10 11:31 下午
     */
    List<String> getSpuDetailImagesBySpuId(@Param("spuId") Long spuId);
}
