package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.CreateAndUpdateBookDTO;
import com.blcheung.zblmissyouadmin.model.BookDO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-22
 */
public interface BookService extends IService<BookDO> {

    /**
     * 获取一本图书
     *
     * @param bookId
     * @return com.blcheung.zblmissyouadmin.model.BookDO
     * @author BLCheung
     * @date 2022/1/22 11:25 下午
     */
    BookDO getOneBook(Long bookId);

    /**
     * 获取所有图书
     *
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.BookDO>
     * @author BLCheung
     * @date 2022/1/22 11:27 下午
     */
    List<BookDO> getAllBook();

    /**
     * 创建图书
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/23 12:02 上午
     */
    Boolean createBook(CreateAndUpdateBookDTO dto);

    /**
     * 删除图书
     *
     * @param bookId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/23 12:05 上午
     */
    Boolean deleteBookById(Long bookId);

    /**
     * 搜索图书
     *
     * @param keyword 关键词
     * @return java.util.List<com.blcheung.zblmissyouadmin.model.BookDO>
     * @author BLCheung
     * @date 2022/1/23 12:14 上午
     */
    List<BookDO> searchBooks(String keyword);
}
