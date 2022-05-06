package com.blcheung.cappuccino.mapper;

import com.blcheung.cappuccino.model.CmsGroupPermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsGroupPermissionMapper extends BaseMapper<CmsGroupPermissionDO> {

    /**
     * 批量插入
     *
     * @param groupPermissionRelations
     * @return int
     * @author BLCheung
     * @date 2022/1/22 4:58 上午
     */
    int saveBatch(@Param("relations") List<CmsGroupPermissionDO> groupPermissionRelations);

    /**
     * 批量删除
     *
     * @param groupId
     * @param permissionIds
     * @return int
     * @author BLCheung
     * @date 2022/1/22 5:17 上午
     */
    int deleteBatch(@Param("groupId") Long groupId, @Param("permissionIds") List<Long> permissionIds);
}
