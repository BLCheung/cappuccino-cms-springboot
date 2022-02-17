package com.blcheung.zblmissyouadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.zblmissyouadmin.model.CategoryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-04
 */
@Repository
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据id获取分类名
     *
     * @param categoryId
     * @return java.lang.String
     * @author BLCheung
     * @date 2022/2/10 10:08 下午
     */
    String getNameById(@Param("categoryId") Long categoryId);
}