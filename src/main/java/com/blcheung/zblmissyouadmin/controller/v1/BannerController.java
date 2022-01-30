package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.permission.LoginRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.CreateBannerDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.BannerService;
import com.blcheung.zblmissyouadmin.vo.BannerVO;
import com.blcheung.zblmissyouadmin.vo.common.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import com.blcheung.zblmissyouadmin.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public CreatedVO createBanner(@RequestBody @Validated CreateBannerDTO dto) {
        Boolean createSuccess = this.bannerService.createBanner(dto);
        if (!createSuccess) throw new FailedException(10300);
        return ResultKit.resolveCreated();
    }

    @GetMapping("/page")
    @LoginRequired
    @RouterMeta(name = "查询Banner", mount = false)
    public ResultVO<PagingVO<BannerVO>> getBanners(BasePagingDTO dto) {
        PagingVO<BannerVO> bannerPaging = this.bannerService.getBannerPage(dto);
        return ResultKit.resolve(bannerPaging);
    }
}
