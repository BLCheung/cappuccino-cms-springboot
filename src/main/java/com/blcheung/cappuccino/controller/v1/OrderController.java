package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.dto.OrderPageDTO;
import com.blcheung.cappuccino.dto.OrderStatusDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.OrderService;
import com.blcheung.cappuccino.vo.OrderDetailVO;
import com.blcheung.cappuccino.vo.OrderVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import com.blcheung.cappuccino.vo.common.ResultVO;
import com.blcheung.cappuccino.vo.common.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/order")
@Validated
@GroupRequired
@RouterModule(name = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有订单", mount = false)
    public ResultVO<PagingVO<OrderVO>> page(@Validated OrderPageDTO dto) {
        PagingVO<OrderVO> orderPage = this.orderService.getOrderPage(dto);
        return ResultKit.resolve(orderPage);
    }

    @GetMapping("/{id}")
    @RouterMeta(name = "获取订单详情", mount = false)
    public ResultVO<OrderDetailVO> get(@PathVariable @Positive Long id) {
        OrderDetailVO detailVO = this.orderService.getOrderDetail(id);
        return ResultKit.resolve(detailVO);
    }

    @PutMapping("/status")
    @RouterMeta(name = "修改订单状态")
    public UpdatedVO status(@RequestBody @Validated OrderStatusDTO dto) {
        Boolean success = this.orderService.changeOrderStatus(dto);
        return ResultKit.resolveUpdated(success);
    }
}
