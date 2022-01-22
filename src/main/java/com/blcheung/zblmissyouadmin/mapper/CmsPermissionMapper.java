package com.blcheung.zblmissyouadmin.mapper;

import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
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
public interface CmsPermissionMapper extends BaseMapper<CmsPermissionDO> {

    /**
     * 通过分组id集合获取所有分组的权限
     *
     * @param groupIds
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/11 2:44 上午
     */
    List<CmsPermissionDO> getPermissionsByGroupIds(@Param("groupIds") List<Long> groupIds);

    /**
     * 根据分组id获取指定分组的所有权限
     *
     * @param groupId
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.CmsPermissionDO>
     * @author BLCheung
     * @date 2022/1/22 12:36 上午
     */
    List<CmsPermissionDO> getPermissionByGroupId(@Param("groupId") Long groupId);

    /**
     * 根据分组id获取指定分组的所有权限id集合
     *
     * @param groupId
     * @return java.util.List<java.lang.Long>
     * @author BLCheung
     * @date 2022/1/22 8:22 下午
     */
    List<Long> getPermissionIdsByGroupId(@Param("groupId") Long groupId);
}
