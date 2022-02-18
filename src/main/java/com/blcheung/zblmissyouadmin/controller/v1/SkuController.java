package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.SkuDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.SkuService;
import com.blcheung.zblmissyouadmin.vo.SkuVO;
import com.blcheung.zblmissyouadmin.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * SKU控制器
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
    @RouterMeta(name = "查询所有SKU", mount = false)
    public ResultVO<PagingVO<SkuVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<SkuVO> skuPage = this.skuService.getSkuPage(dto);
        return ResultKit.resolve(skuPage);
    }

    @PostMapping
    @RouterMeta(name = "创建SKU")
    public CreatedVO create(@RequestBody @Validated SkuDTO dto) {
        SkuVO skuVO = this.skuService.create(dto);
        return ResultKit.resolveCreated(skuVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新SKU")
    public UpdatedVO update(@PathVariable(value = "id") @Positive Long skuId, @RequestBody @Validated SkuDTO dto) {
        SkuVO skuVO = this.skuService.update(skuId, dto);
        return ResultKit.resolveUpdated(skuVO);
    }

    @DeleteMapping("{id}")
    @RouterMeta(name = "删除SKU")
    public DeletedVO delete(@PathVariable(value = "id") @Positive Long skuId) {
        Boolean isDeleted = this.skuService.delete(skuId);
        if (!isDeleted) throw new FailedException(10554);
        return ResultKit.resolveDeleted();
    }
}
