package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.SpuDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.SpuService;
import com.blcheung.zblmissyouadmin.vo.SpuDetailVO;
import com.blcheung.zblmissyouadmin.vo.SpuVO;
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
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/spu")
@Validated
@GroupRequired
@RouterModule(name = "SPU")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有SPU", mount = false)
    public ResultVO<PagingVO<SpuVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<SpuVO> spuPage = this.spuService.getSpuPage(dto);
        return ResultKit.resolve(spuPage);
    }

    @GetMapping("/{id}")
    @RouterMeta(name = "获取SPU详情", mount = false)
    public ResultVO<SpuDetailVO> get(@PathVariable @Positive Long id) {
        SpuDetailVO spuDetailVO = this.spuService.getSpuDetail(id);
        return ResultKit.resolve(spuDetailVO);
    }

    @PostMapping
    @RouterMeta(name = "创建SPU")
    public CreatedVO create(@RequestBody @Validated SpuDTO dto) {
        SpuVO spuVO = this.spuService.create(dto);
        return ResultKit.resolveCreated(spuVO);
    }

    @PutMapping("{id}")
    @RouterMeta(name = "更新SPU")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated SpuDTO dto) {
        SpuVO spuVO = this.spuService.update(id, dto);
        return ResultKit.resolveUpdated(spuVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除SPU")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.spuService.delete(id);
        if (!isDeleted) throw new FailedException(10504);
        return ResultKit.resolveDeleted();
    }
}
