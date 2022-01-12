package com.blcheung.zblmissyouadmin.mapper;

import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Repository
public interface CmsGroupMapper extends BaseMapper<CmsGroupDO> {

    /**
     * 获取用户的所有分组id
     *
     * @param uid
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/11 3:13 上午
     */
    List<Long> getGroupIdsByUserId(@Param("uid") Long uid);

    /**
     * 获取用户的所有分组
     *
     * @param uid
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsGroupDO>
     * @author BLCheung
     * @date 2022/1/11 3:39 上午
     */
    List<CmsGroupDO> getGroupsByUserId(@Param("uid") Long uid);
}
