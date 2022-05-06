package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.SpecKeyDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.SpecKeyService;
import com.blcheung.cappuccino.vo.SpecKeyVO;
import com.blcheung.cappuccino.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 规格名控制器
 *
 * @author BLCheung
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/spec-key")
@Validated
@GroupRequired
@RouterModule(name = "规格管理")
public class SpecKeyController {

    @Autowired
    private SpecKeyService specKeyService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有规格名", mount = false)
    public ResultVO<PagingVO<SpecKeyVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<SpecKeyVO> pagingVO = this.specKeyService.getSpecKeyPage(dto);
        return ResultKit.resolve(pagingVO);
    }

    @GetMapping("/by/spu/{id}")
    @RouterMeta(name = "查询Spu下的规格名", mount = false)
    public ResultVO<List<SpecKeyVO>> getSpecBySpu(@PathVariable(value = "id") @Positive Long spuId) {
        List<SpecKeyVO> specKeys = this.specKeyService.getSpecKeysBySpuId(spuId);
        return ResultKit.resolve(specKeys);
    }

    @PostMapping
    @RouterMeta(name = "创建规格名")
    public CreatedVO create(@RequestBody @Validated SpecKeyDTO dto) {
        SpecKeyVO specKeyVO = this.specKeyService.create(dto);
        return ResultKit.resolveCreated(specKeyVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新规格名")
    public UpdatedVO update(@PathVariable(value = "id") @Positive Long specKeyId,
                            @RequestBody @Validated SpecKeyDTO dto) {
        SpecKeyVO specKeyVO = this.specKeyService.update(specKeyId, dto);
        return ResultKit.resolveUpdated(specKeyVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除规格名")
    public DeletedVO delete(@PathVariable(value = "id") @Positive Long specKeyId) {
        Boolean isDeleted = this.specKeyService.delete(specKeyId);
        if (!isDeleted) throw new FailedException(10604);
        return ResultKit.resolveDeleted();
    }
}
