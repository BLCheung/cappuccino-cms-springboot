package com.blcheung.zblmissyouadmin.vo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/23 10:28 下午
 */
@Getter
@Setter
public class PagingVO<T> {

    private Boolean hasNext;

    private Long pageNum;

    private Long pageSize;

    private Long total;

    private Long totalPage;

    private List<T> list;

    /**
     * 默认分页结果构造函数
     * 最终会构造一个数据源类型为item的分页结果
     *
     * @param page 来自数据源的page分页器
     * @author BLCheung
     * @date 2022/1/24 9:57 下午
     */
    public PagingVO(Page<T> page) {
        this.initPage(page);
        this.list = page.getRecords();
    }

    /**
     * 视图对象分页结果构造函数
     * 最终会构造一个为目标视图类型为item的分页结果
     *
     * @param page   来自数据源的page分页器
     * @param voItem 需要转换的目标视图对象
     * @author BLCheung
     * @date 2022/1/24 9:58 下午
     */
    public <D> PagingVO(Page<D> page, T voItem) {
        this.initPage(page);
        this.list = BeanKit.transformList(page.getRecords(), voItem);
    }

    /**
     * 视图类型的构造函数
     * 最终会构造一个为目标视图类型为item的分页结果
     *
     * @param page    来自数据源的page分页器
     * @param voClazz 需要转换的目标视图类
     * @author BLCheung
     * @date 2022/1/24 10:02 下午
     */
    public <D> PagingVO(Page<D> page, Class<T> voClazz) {
        this.initPage(page);
        this.list = BeanKit.transformList(page.getRecords(), voClazz);
    }

    /**
     * 集合类型的构造函数
     * 最终会构造一个为目标类型的集合视图结果分页
     *
     * @param page   来自数据源的page分页器
     * @param voList 视图类型的集合
     * @author BLCheung
     * @date 2022/1/24 10:05 下午
     */
    public <D> PagingVO(Page<D> page, List<T> voList) {
        this.initPage(page);
        this.list = voList;
    }

    private <D> void initPage(Page<D> page) {
        this.hasNext   = page.hasNext();
        this.pageNum   = page.getCurrent();
        this.pageSize  = page.getSize();
        this.total     = page.getTotal();
        this.totalPage = page.getPages();
    }
}
