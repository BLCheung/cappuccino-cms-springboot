package com.blcheung.cappuccino.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.cappuccino.model.SpuImgDO;
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
public interface SpuImgMapper extends BaseMapper<SpuImgDO> {

    /**
     * 获取spu的轮播图
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author BLCheung
     * @date 2022/2/10 11:24 下午
     */
    List<String> getSpuImagesBySpuId(@Param("spuId") Long spuId);
}
