package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.dto.ThemeDTO;
import com.blcheung.cappuccino.dto.ThemeSpuDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.service.ThemeService;
import com.blcheung.cappuccino.vo.SpuVO;
import com.blcheung.cappuccino.vo.ThemeVO;
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
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/theme")
@Validated
@GroupRequired
@RouterModule(name = "主题")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping("")
    @RouterMeta(name = "创建主题")
    public CreatedVO create(@RequestBody @Validated ThemeDTO dto) {
        ThemeVO themeVO = this.themeService.createTheme(dto);
        return ResultKit.resolveCreated(themeVO);
    }

    @GetMapping("{id}")
    @RouterMeta(name = "查看主题详情", mount = false)
    public ResultVO<ThemeVO> get(@PathVariable @Positive Long id) {
        ThemeVO themeVO = this.themeService.getThemeDetail(id);
        return ResultKit.resolve(themeVO);
    }

    @PutMapping("/{id}")
    @RouterMeta(name = "更新主题")
    public UpdatedVO update(@PathVariable @Positive Long id, @RequestBody @Validated ThemeDTO dto) {
        ThemeVO themeVO = this.themeService.updateTheme(id, dto);
        return ResultKit.resolveUpdated(themeVO);
    }

    @DeleteMapping("{id}")
    @RouterMeta(name = "删除主题")
    public DeletedVO delete(@PathVariable @Positive Long id) {
        Boolean isDeleted = this.themeService.deleteTheme(id);
        if (!isDeleted) throw new FailedException(10454);
        return ResultKit.resolveDeleted();
    }

    @GetMapping("/page")
    @RouterMeta(name = "查看所有主题", mount = false)
    public ResultVO<PagingVO<ThemeVO>> page(@Validated BasePagingDTO dto) {
        PagingVO<ThemeVO> themePage = this.themeService.getThemePage(dto);
        return ResultKit.resolve(themePage);
    }

    @GetMapping("/{id}/spus")
    @RouterMeta(name = "获取主题下所有SPU", mount = false)
    public ResultVO<List<SpuVO>> spus(@PathVariable @Positive Long id) {
        List<SpuVO> themeSpus = this.themeService.getThemeSpus(id);
        return ResultKit.resolve(themeSpus);
    }

    @PostMapping("/spu")
    @RouterMeta(name = "主题下添加SPU")
    public CreatedVO addSpu(@RequestBody @Validated ThemeSpuDTO dto) {
        SpuVO spuVO = this.themeService.addThemeSpu(dto);
        return ResultKit.resolveCreated(spuVO);
    }

    @DeleteMapping("/spu")
    @RouterMeta(name = "删除主题下的SPU")
    public DeletedVO deleteSpu(@RequestBody @Validated ThemeSpuDTO dto) {
        Boolean isDeleted = this.themeService.deleteThemeSpu(dto);
        if (!isDeleted) throw new FailedException(10459);
        return ResultKit.resolveDeleted();
    }
}
