package com.blcheung.zblmissyouadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blcheung.zblmissyouadmin.model.OrderDO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-27
 */
public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 改变订单状态
     *
     * @param orderId
     * @param status
     * @return int
     * @author BLCheung
     * @date 2022/2/28 3:47 上午
     */
    int changeOrderStatus(@Param("orderId") Long orderId, @Param("status") Integer status);
}
