package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.SkuService;
import com.blcheung.zblmissyouadmin.vo.SkuVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import com.blcheung.zblmissyouadmin.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/sku")
@Validated
@GroupRequired
@RouterModule(name = "SKU")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有Sku", mount = false)
    public ResultVO<PagingVO<SkuVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<SkuVO> skuPage = this.skuService.getSkuPage(dto);
        return ResultKit.resolve(skuPage);
    }


}
