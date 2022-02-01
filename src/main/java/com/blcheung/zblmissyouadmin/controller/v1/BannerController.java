package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.permission.LoginRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.BannerDTO;
import com.blcheung.zblmissyouadmin.dto.BannerItemDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.BannerService;
import com.blcheung.zblmissyouadmin.vo.BannerItemVO;
import com.blcheung.zblmissyouadmin.vo.BannerVO;
import com.blcheung.zblmissyouadmin.vo.BannerWithItemsVO;
import com.blcheung.zblmissyouadmin.vo.common.*;
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
 * @since 2022-01-30
 */
@RestController
@RequestMapping("/banner")
@Validated
@RouterModule(name = "Banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping
    @GroupRequired
    @RouterMeta(name = "创建Banner")
    public CreatedVO createBanner(@RequestBody @Validated BannerDTO dto) {
        Boolean createSuccess = this.bannerService.createBanner(dto);
        if (!createSuccess) throw new FailedException(10300);
        return ResultKit.resolveCreated();
    }

    @PutMapping("/{id}")
    @GroupRequired
    @RouterMeta(name = "更新Banner")
    public ResultVO<BannerVO> updateBanner(@PathVariable @Positive Long id, @RequestBody @Validated BannerDTO dto) {
        BannerVO bannerVO = this.bannerService.updateBanner(id, dto);
        return ResultKit.resolve(bannerVO);
    }

    @DeleteMapping("/{id}")
    @GroupRequired
    @RouterMeta(name = "删除Banner")
    public DeletedVO deleteBanner(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.bannerService.deleteBanner(id);
        if (!isDeleted) throw new FailedException(10300);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/{id}")
    @LoginRequired
    @RouterMeta(name = "查询Banner详情", mount = false)
    public ResultVO<BannerWithItemsVO> getWithItems(@PathVariable @Positive Long id) {
        BannerWithItemsVO bannerWhitItemVO = this.bannerService.getBanner(id);
        return ResultKit.resolve(bannerWhitItemVO);
    }

    @GetMapping("/page")
    @LoginRequired
    @RouterMeta(name = "查询Banner分页", mount = false)
    public ResultVO<PagingVO<BannerVO>> getBanners(BasePagingDTO dto) {
        PagingVO<BannerVO> bannerPaging = this.bannerService.getBannerPage(dto);
        return ResultKit.resolve(bannerPaging);
    }

    @PostMapping("/item")
    @GroupRequired
    @RouterMeta(name = "创建Banner Item")
    public CreatedVO createItem(@RequestBody BannerItemDTO dto) {
        BannerItemVO bannerItemVO = this.bannerService.createItem(dto);
        return ResultKit.resolveCreated(bannerItemVO);
    }

    @PutMapping("/item/{id}")
    @GroupRequired
    @RouterMeta(name = "更新Banner Item")
    public UpdatedVO updateItem(@PathVariable @Positive Long id, @RequestBody BannerItemDTO dto) {
        BannerItemVO bannerItemVO = this.bannerService.updateItem(id, dto);
        return ResultKit.resolveUpdated(bannerItemVO);
    }

    @DeleteMapping("/item/{id}")
    @GroupRequired
    @RouterMeta(name = "删除Banner Item")
    public DeletedVO deleteItem(@PathVariable @Positive Long id) {
        Boolean deleteSuccess = this.bannerService.deleteItem(id);
        if (!deleteSuccess) throw new FailedException(10300);
        return ResultKit.resolveDeleted();
    }
}
