package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.OrderPageDTO;
import com.blcheung.cappuccino.dto.OrderStatusDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.OrderMapper;
import com.blcheung.cappuccino.model.OrderDO;
import com.blcheung.cappuccino.service.CouponService;
import com.blcheung.cappuccino.service.OrderService;
import com.blcheung.cappuccino.vo.CouponVO;
import com.blcheung.cappuccino.vo.OrderDetailVO;
import com.blcheung.cappuccino.vo.OrderVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Autowired
    private CouponService couponService;

    @Override
    public OrderDO getOrder(Long orderId) {
        return this.lambdaQuery()
                   .eq(orderId != null, OrderDO::getId, orderId)
                   .last("limit 1")
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10751));
    }

    @Override
    public void checkOrderExist(Long orderId) {
        Long count = this.lambdaQuery()
                         .select(OrderDO::getId)
                         .eq(orderId != null, OrderDO::getId, orderId)
                         .last("limit 1")
                         .count();
        if (count <= 0) throw new NotFoundException(10751);
    }

    @Override
    public PagingVO<OrderVO> getOrderPage(OrderPageDTO dto) {
        Page<OrderDO> pageable = PagingKit.buildLatest(dto, OrderDO.class);

        pageable = this.lambdaQuery()
                       .like(!StringUtils.isEmpty(dto.getKeyword()), OrderDO::getId, dto.getKeyword())
                       .or()
                       .like(!StringUtils.isEmpty(dto.getKeyword()), OrderDO::getOrderNo, dto.getKeyword())
                       .and(!ObjectUtils.isEmpty(dto.getStartTime()) || !ObjectUtils.isEmpty(dto.getEndTime()),
                            o -> o.gt(!ObjectUtils.isEmpty(dto.getStartTime()), OrderDO::getCreateTime,
                                      dto.getStartTime())
                                  .lt(!ObjectUtils.isEmpty(dto.getEndTime()), OrderDO::getCreateTime, dto.getEndTime()))
                       .page(pageable);

        return PagingKit.resolve(pageable, new OrderVO());
    }

    @Override
    public OrderDetailVO getOrderDetail(Long orderId) {
        OrderDO orderDO = this.getOrder(orderId);

        List<CouponVO> coupons = this.couponService.getCouponsByOrderAndUser(orderDO.getId(), orderDO.getUserId());

        return BeanKit.transform(orderDO, new OrderDetailVO(coupons));
    }

    @Transactional
    @Override
    public Boolean changeOrderStatus(OrderStatusDTO dto) {
        this.checkOrderExist(dto.getOrderId());

        int count = this.getBaseMapper()
                        .changeOrderStatus(dto.getOrderId(), dto.getStatus());
        if (count <= 0) throw new FailedException(10752);

        return true;
    }
}
