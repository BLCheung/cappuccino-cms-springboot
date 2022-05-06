package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.dto.OrderPageDTO;
import com.blcheung.cappuccino.dto.OrderStatusDTO;
import com.blcheung.cappuccino.model.OrderDO;
import com.blcheung.cappuccino.vo.OrderDetailVO;
import com.blcheung.cappuccino.vo.OrderVO;
import com.blcheung.cappuccino.vo.common.PagingVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-27
 */
public interface OrderService extends IService<OrderDO> {

    /**
     * 获取订单
     *
     * @param orderId
     * @return com.blcheung.cappuccino.model.OrderDO
     * @author BLCheung
     * @date 2022/2/28 1:25 上午
     */
    OrderDO getOrder(Long orderId);

    /**
     * 检查订单是否存在
     *
     * @param orderId
     * @author BLCheung
     * @date 2022/2/28 3:44 上午
     */
    void checkOrderExist(Long orderId);

    /**
     * 查询订单分页
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.OrderVO>
     * @author BLCheung
     * @date 2022/2/27 11:54 下午
     */
    PagingVO<OrderVO> getOrderPage(OrderPageDTO dto);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return com.blcheung.cappuccino.vo.OrderDetailVO
     * @author BLCheung
     * @date 2022/2/28 1:24 上午
     */
    OrderDetailVO getOrderDetail(Long orderId);

    /**
     * 改变订单状态
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/28 3:43 上午
     */
    Boolean changeOrderStatus(OrderStatusDTO dto);
}
