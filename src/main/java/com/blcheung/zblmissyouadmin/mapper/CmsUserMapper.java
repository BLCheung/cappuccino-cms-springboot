package com.blcheung.zblmissyouadmin.mapper;

import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsUserMapper extends BaseMapper<CmsUserDO> {

    /**
     * 通过id查找人数
     *
     * @param id
     * @return java.lang.Integer
     * @author BLCheung
     * @date 2021/12/17 10:49 下午
     */
    Integer checkCountById(Long id);

    /**
     * 通过用户名查找人数
     *
     * @param username
     * @return java.lang.Integer
     * @author BLCheung
     * @date 2021/12/17 10:50 下午
     */
    Integer checkCountByUsername(String username);
}
