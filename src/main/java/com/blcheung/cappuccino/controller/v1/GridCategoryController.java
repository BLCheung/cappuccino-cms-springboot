package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.GridCategoryDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.GridCategoryService;
import com.blcheung.cappuccino.vo.GridCategoryVO;
import com.blcheung.cappuccino.vo.common.CreatedVO;
import com.blcheung.cappuccino.vo.common.DeletedVO;
import com.blcheung.cappuccino.vo.common.ResultVO;
import com.blcheung.cappuccino.vo.common.UpdatedVO;
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
 * @since 2022-02-06
 */
@RestController
@RequestMapping("/grid-category")
@Validated
@GroupRequired
@RouterModule(name = "六宫格")
public class GridCategoryController {

    @Autowired
    private GridCategoryService gridCategoryService;

    @PostMapping("")
    @RouterMeta(name = "创建宫格")
    public CreatedVO create(@RequestBody @Validated GridCategoryDTO dto) {
        GridCategoryVO gridCategoryVO = this.gridCategoryService.createGirdCategory(dto);
        return ResultKit.resolveCreated(gridCategoryVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新宫格")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated GridCategoryDTO dto) {
        GridCategoryVO gridCategoryVO = this.gridCategoryService.updateGridCategory(id, dto);
        return ResultKit.resolveUpdated(gridCategoryVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除宫格")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.gridCategoryService.deleteGridCategory(id);
        if (!isDeleted) throw new FailedException(10424);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/all")
    @RouterMeta(name = "查看所有宫格", mount = false)
    public ResultVO<List<GridCategoryVO>> all() {
        List<GridCategoryVO> allGridCategory = this.gridCategoryService.getAllGridCategory();
        return ResultKit.resolve(allGridCategory);
    }
}
