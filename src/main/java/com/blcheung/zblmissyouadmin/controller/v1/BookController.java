package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.permission.LoginRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.CreateAndUpdateBookDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.model.BookDO;
import com.blcheung.zblmissyouadmin.service.BookService;
import com.blcheung.zblmissyouadmin.vo.common.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.common.DeletedVO;
import com.blcheung.zblmissyouadmin.vo.common.ResultVO;
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
