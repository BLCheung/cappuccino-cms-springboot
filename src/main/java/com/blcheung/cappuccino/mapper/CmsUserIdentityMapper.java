package com.blcheung.cappuccino.mapper;

import com.blcheung.cappuccino.model.CmsUserIdentityDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Repository
public interface CmsUserIdentityMapper extends BaseMapper<CmsUserIdentityDO> {

    /**
     * 通过用户id移除凭证
     *
     * @param userId
     * @return int
     * @author BLCheung
     * @date 2022/1/27 10:03 下午
     */
    int deleteByUserId(@Param("userId") Long userId);
}
