package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.dto.ThemeDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.ThemeService;
import com.blcheung.zblmissyouadmin.vo.ThemeVO;
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
}
