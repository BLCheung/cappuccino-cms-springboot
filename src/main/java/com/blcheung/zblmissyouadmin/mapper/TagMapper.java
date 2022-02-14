package com.blcheung.zblmissyouadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.zblmissyouadmin.model.TagDO;
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
public interface TagMapper extends BaseMapper<TagDO> {

    /**
     * 根据spu的id获取spu下的标签
     *
     * @param spuId
     * @return java.util.List<java.lang.String>
     * @author BLCheung
     * @date 2022/2/10 11:07 下午
     */
    List<String> getTagsNameBySpuId(@Param("spuId") Long spuId);
}
