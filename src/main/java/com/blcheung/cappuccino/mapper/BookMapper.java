package com.blcheung.cappuccino.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.cappuccino.model.BookDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-22
 */
@Repository
public interface BookMapper extends BaseMapper<BookDO> {}
