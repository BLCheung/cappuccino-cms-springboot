package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.SpecKeyDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.SpecKeyService;
import com.blcheung.zblmissyouadmin.vo.SpecKeyVO;
import com.blcheung.zblmissyouadmin.vo.common.*;
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
