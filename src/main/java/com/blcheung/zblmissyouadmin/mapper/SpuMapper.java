package com.blcheung.zblmissyouadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.zblmissyouadmin.bo.SpuDetailBO;
import com.blcheung.zblmissyouadmin.model.SpuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * @return com.blcheung.zblmissyouadmin.bo.SpuDetailBO
     * @author BLCheung
     * @date 2022/2/11 12:14 上午
     */
    SpuDetailBO getSpuDetail(@Param("spu") SpuDO spuDO);
}
