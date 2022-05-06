package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.CreateAndUpdateBookDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.mapper.BookMapper;
import com.blcheung.cappuccino.model.BookDO;
import com.blcheung.cappuccino.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-22
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookDO> implements BookService {

    @Override
    public BookDO getOneBook(Long bookId) {
        return this.lambdaQuery()
                   .eq(BookDO::getId, bookId)
                   .one();
    }

    @Override
    public List<BookDO> getAllBook() {
        return this.lambdaQuery()
                   .isNull(BookDO::getDeleteTime)
                   .list();
    }

    @Override
    public Boolean createBook(CreateAndUpdateBookDTO dto) {
        BookDO bookDO = BeanKit.transform(dto, new BookDO());
        return this.getBaseMapper()
                   .insert(bookDO) > 0;
    }

    @Override
    public Boolean deleteBookById(Long bookId) {
        this.validateBookExist(bookId);
        return this.getBaseMapper()
                   .deleteById(bookId) > 0;
    }

    @Override
    public List<BookDO> searchBooks(String keyword) {
        // TODO: 搜索图书
        return null;
    }


    private void validateBookExist(Long bookId) {
        this.lambdaQuery()
            .select(BookDO::getId)
            .eq(!ObjectUtils.isEmpty(bookId), BookDO::getId, bookId)
            .oneOpt()
            .orElseThrow(() -> new NotFoundException(10004, "图书不存在"));
    }
}
