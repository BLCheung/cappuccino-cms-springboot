package com.blcheung.zblmissyouadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.zblmissyouadmin.model.SpecKeyDO;
import com.blcheung.zblmissyouadmin.model.SpecKeyValueDO;
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
public interface SpecKeyMapper extends BaseMapper<SpecKeyDO> {

    /**
     * 根据id获取规格名
     *
     * @param specKeyId
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/2/10 10:07 下午
     */
    String getSpecKeyNameById(@Param("specKeyId") Long specKeyId);

    /**
     * 根据spu的id获取所有规格名
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author BLCheung
     * @date 2022/2/10 10:33 下午
     */
    List<String> getSpecKeyNamesBySpuId(@Param("spuId") Long spuId);

    /**
     * 保存spu的specKey
     *
     * @param spuId
     * @param spuKeys
     * @return int
     * @author BLCheung
     * @date 2022/2/14 12:33 上午
     */
    int saveSpecKeyBySpuId(@Param("spuId") Long spuId, @Param("spuKeys") List<Long> spuKeys);

    /**
     * 通过spuId获取该Spu下所属规格名
     *
     * @param spuId
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.SpecKeyDO>
     * @author BLCheung
     * @date 2022/2/16 11:23 下午
     */
    List<SpecKeyDO> getSpecKeysBySpuId(@Param("spuId") Long spuId);

    /**
     * 获取规格名与规格值
     *
     * @param keyId
     * @param valueId
     * @return com.blcheung.zblmissyouadmin.model.SpecKeyValueDO
     * @author BLCheung
     * @date 2022/2/18 9:52 下午
     */
    SpecKeyValueDO getSpecKeyValueById(@Param("keyId") Long keyId, @Param("valueId") Long valueId);
}
