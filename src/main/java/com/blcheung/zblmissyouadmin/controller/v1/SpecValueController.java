package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.SpecValueDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.SpecValueService;
import com.blcheung.zblmissyouadmin.vo.SpecValueVO;
import com.blcheung.zblmissyouadmin.vo.common.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.common.DeletedVO;
import com.blcheung.zblmissyouadmin.vo.common.ResultVO;
import com.blcheung.zblmissyouadmin.vo.common.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 规格值控制器
 *
 * @author BLCheung
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/spec-value")
@Validated
@GroupRequired
@RouterModule(name = "规格管理")
public class SpecValueController {

    @Autowired
    private SpecValueService specValueService;

    @GetMapping("/by/spec-key/{id}")
    @RouterMeta(name = "查询规格名下所有规格值", mount = false)
    public ResultVO<List<SpecValueVO>> getSpecValuesBySpecKey(@PathVariable(value = "id") @Positive Long specKeyId) {
        List<SpecValueVO> specValues = this.specValueService.getSpecValuesBySpecKey(specKeyId);
        return ResultKit.resolve(specValues);
    }

    @PostMapping
    @RouterMeta(name = "创建规格值")
    public CreatedVO create(@RequestBody @Validated SpecValueDTO dto) {
        SpecValueVO specValueVO = this.specValueService.create(dto);
        return ResultKit.resolveCreated(specValueVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新规格值")
    public UpdatedVO update(@PathVariable(value = "id") @Positive Long specValueId,
                            @RequestBody @Validated SpecValueDTO dto) {
        SpecValueVO specValueVO = this.specValueService.update(specValueId, dto);
        return ResultKit.resolveUpdated(specValueVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除规格值")
    public DeletedVO delete(@PathVariable(value = "id") @Positive Long specValueId) {
        Boolean isDeleted = this.specValueService.delete(specValueId);
        if (!isDeleted) throw new FailedException(10629);
        return ResultKit.resolveDeleted();
    }
}
