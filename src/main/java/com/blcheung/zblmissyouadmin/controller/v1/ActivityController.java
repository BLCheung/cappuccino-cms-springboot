package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.ActivityCouponDTO;
import com.blcheung.zblmissyouadmin.dto.ActivityDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.ActivityService;
import com.blcheung.zblmissyouadmin.vo.ActivityVO;
import com.blcheung.zblmissyouadmin.vo.CouponVO;
import com.blcheung.zblmissyouadmin.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-03
 */
@RestController
@RequestMapping("/activity")
@Validated
@GroupRequired
@RouterModule(name = "活动")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有活动", mount = false)
    public ResultVO<PagingVO<ActivityVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<ActivityVO> pagingVO = this.activityService.getActivityPage(dto);
        return ResultKit.resolve(pagingVO);
    }

    @GetMapping("/{id}")
    @RouterMeta(name = "获取活动详情", mount = false)
    public ResultVO<ActivityVO> get(@PathVariable @Positive Long id) {
        ActivityVO activityVO = this.activityService.getActivityDetail(id);
        return ResultKit.resolve(activityVO);
    }

    @PostMapping
    @RouterMeta(name = "创建活动")
    public CreatedVO create(@RequestBody @Validated ActivityDTO dto) {
        ActivityVO activityVO = this.activityService.createActivity(dto);
        return ResultKit.resolveCreated(activityVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新活动")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated ActivityDTO dto) {
        ActivityVO activityVO = this.activityService.updateActivity(id, dto);
        return ResultKit.resolveUpdated(activityVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除活动")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.activityService.deleteActivity(id);
        if (!isDeleted) throw new FailedException(10354);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/{id}/coupons")
    @RouterMeta(name = "获取活动下所有优惠券", mount = false)
    public ResultVO<List<CouponVO>> getCoupons(@PathVariable(value = "id") @Positive Long activityId) {
        List<CouponVO> coupons = this.activityService.getActivityCoupons(activityId);
        return ResultKit.resolve(coupons);
    }

    @PostMapping("/coupon")
    @RouterMeta(name = "活动添加优惠券")
    public CreatedVO addCoupon(@RequestBody @Validated ActivityCouponDTO dto) {
        CouponVO couponVO = this.activityService.activityAddCoupon(dto);
        return ResultKit.resolveCreated(couponVO);
    }

    @DeleteMapping("/coupon")
    @RouterMeta(name = "活动删除优惠券")
    public DeletedVO deleteCoupon(@RequestBody @Validated ActivityCouponDTO dto) {
        Boolean isDeleted = this.activityService.activityDeleteCoupon(dto);
        if (!isDeleted) throw new FailedException(10358);
        return ResultKit.resolveDeleted();
    }
}
