package com.blcheung.zblmissyouadmin.mapper;

import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface CmsUserGroupMapper extends BaseMapper<CmsUserGroupDO> {

    /**
     * 批量插入
     *
     * @param relations
     * @return int
     * @author BLCheung
     * @date 2022/1/25 8:29 下午
     */
    int saveBatch(@Param("relations") List<CmsUserGroupDO> relations);

    /**
     * 批量删除
     *
     * @param userId
     * @param deleteGroupIds
     * @return int
     * @author BLCheung
     * @date 2022/1/25 8:29 下午
     */
    int deleteBatch(@Param("userId") Long userId, @Param("groupIds") List<Long> deleteGroupIds);
}
