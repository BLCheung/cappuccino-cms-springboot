package com.blcheung.cappuccino.controller.v1;


import com.blcheung.cappuccino.common.annotations.permission.GroupRequired;
import com.blcheung.cappuccino.common.annotations.permission.LoginRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.CreateAndUpdateBookDTO;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.model.BookDO;
import com.blcheung.cappuccino.service.BookService;
import com.blcheung.cappuccino.vo.common.CreatedVO;
import com.blcheung.cappuccino.vo.common.DeletedVO;
import com.blcheung.cappuccino.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-22
 */
@RestController
@RequestMapping("/book")
@Validated
@LoginRequired
@RouterModule(name = "图书")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    @RouterMeta(name = "获取一本图书", mount = false)
    public ResultVO<BookDO> getBooks(@PathVariable @Positive Long id) {
        BookDO book = this.bookService.getOneBook(id);
        if (ObjectUtils.isEmpty(book)) throw new NotFoundException(10004, "找不到图书");
        return ResultKit.resolve(book);
    }

    @PostMapping
    @GroupRequired
    @RouterMeta(name = "创建图书")
    public CreatedVO createBook(@RequestBody @Validated CreateAndUpdateBookDTO dto) {
        this.bookService.createBook(dto);
        return ResultKit.resolveCreated();
    }

    @DeleteMapping("/{id}")
    @GroupRequired
    @RouterMeta(name = "删除图书")
    public DeletedVO deleteBook(@PathVariable @Positive Long id) {
        this.bookService.deleteBookById(id);
        return ResultKit.resolveDeleted();
    }
}
