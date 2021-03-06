package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.CategoryDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.CategoryService;
import com.blcheung.cappuccino.vo.CategoryVO;
import com.blcheung.cappuccino.vo.RootCategoryVO;
import com.blcheung.cappuccino.vo.common.*;
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
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/category")
@Validated
@GroupRequired
@RouterModule(name = "分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    @RouterMeta(name = "创建分类")
    public CreatedVO create(@RequestBody @Validated CategoryDTO dto) {
        CategoryVO categoryVO = this.categoryService.createCategory(dto);
        return ResultKit.resolveCreated(categoryVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新分类")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated CategoryDTO dto) {
        CategoryVO categoryVO = this.categoryService.updateCategory(id, dto);
        return ResultKit.resolveUpdated(categoryVO);
    }

    @DeleteMapping("/{id}")
    @RouterMeta(name = "删除分类")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.categoryService.deleteCategory(id);
        if (!isDeleted) throw new FailedException(10404);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/page")
    @RouterMeta(name = "查询所有分类", mount = false)
    public ResultVO<PagingVO<CategoryVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<CategoryVO> categoryPage = this.categoryService.getCategoryPage(dto);
        return ResultKit.resolve(categoryPage);
    }

    @GetMapping("/{id}/page")
    @RouterMeta(name = "查看子分类", mount = false)
    public ResultVO<PagingVO<CategoryVO>> subPage(@PathVariable @Positive Long id, @Validated BasePagingDTO dto) {
        PagingVO<CategoryVO> subCategoryPage = this.categoryService.getSubCategoryPage(id, dto);
        return ResultKit.resolve(subCategoryPage);
    }

    @GetMapping("/tree")
    @RouterMeta(name = "获取分类树结构", mount = false)
    public ResultVO<List<RootCategoryVO>> getCategoryTree() {
        List<RootCategoryVO> categoryTree = this.categoryService.getAllCategoryTree();
        return ResultKit.resolve(categoryTree);
    }
}
