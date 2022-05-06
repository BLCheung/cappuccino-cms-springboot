package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.CouponDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.CouponService;
import com.blcheung.cappuccino.vo.CouponDetailVO;
import com.blcheung.cappuccino.vo.CouponVO;
import com.blcheung.cappuccino.vo.common.*;
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
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/coupon")
@Validated
@GroupRequired
@RouterModule(name = "优惠券")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有优惠券", mount = false)
    public ResultVO<PagingVO<CouponVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<CouponVO> couponPage = this.couponService.getCouponPage(dto);
        return ResultKit.resolve(couponPage);
    }

    @GetMapping("/{id}")
    @RouterMeta(name = "查看优惠券详情", mount = false)
    public ResultVO<CouponDetailVO> get(@PathVariable @Positive Long id) {
        CouponDetailVO couponVO = this.couponService.getCouponDetail(id);
        return ResultKit.resolve(couponVO);
    }

    @PostMapping
    @RouterMeta(name = "创建优惠券")
    public CreatedVO create(@RequestBody @Validated CouponDTO dto) {
        CouponVO couponVO = this.couponService.createCoupon(dto);
        return ResultKit.resolveCreated(couponVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新优惠券")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated CouponDTO dto) {
        CouponVO couponVO = this.couponService.updateCoupon(id, dto);
        return ResultKit.resolveUpdated(couponVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除优惠券")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.couponService.deleteCoupon(id);
        if (!isDeleted) throw new FailedException(10654);
        return ResultKit.resolveDeleted();
    }
}
