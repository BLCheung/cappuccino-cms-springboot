package com.blcheung.cappuccino.kit;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.dto.common.SortPagingDTO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 分页辅助器
 *
 * @author BLCheung
 * @date 2022/1/21 9:02 下午
 */
public class PagingKit {

    /**
     * 生成分页查询器
     *
     * @param dto         分页DTO
     * @param entityClazz 数据源类型
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>
     * @author BLCheung
     * @date 2022/1/23 10:50 下午
     */
    public static <D extends BasePagingDTO, T> Page<T> build(D dto, Class<T> entityClazz) {
        Long pageNum = dto.getPageNum();
        Long pageSize = dto.getPageSize();
        return new Page<T>(pageNum, pageSize);
    }

    /**
     * 生成分页排序查询器
     *
     * @param dto         分页DTO
     * @param entityClazz 数据源类型
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>
     * @author BLCheung
     * @date 2022/4/12 1:50 上午
     */
    public static <S extends SortPagingDTO, T> Page<T> buildSort(S dto, Class<T> entityClazz) {
        return handleSorts(build(dto, entityClazz), dto);
    }

    /**
     * 生成最新的创建时间的分页查询器
     *
     * @param dto
     * @param entityClazz
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<T>
     * @author BLCheung
     * @date 2022/2/26 3:57 下午
     */
    public static <D extends BasePagingDTO, T> Page<T> buildLatest(D dto, Class<T> entityClazz) {
        Long pageNum = dto.getPageNum();
        Long pageSize = dto.getPageSize();
        return new Page<T>(pageNum, pageSize).addOrder(OrderItem.desc("create_time"));
    }

    /**
     * 构造数据源分页结果
     *
     * @param pageable 分页器
     * @return com.blcheung.cappuccino.vo.PagingResultVO<T>
     * @author BLCheung
     * @date 2022/1/23 10:51 下午
     */
    public static <D> PagingVO<D> resolve(Page<D> pageable) {
        return new PagingVO<D>(pageable);
    }

    /**
     * 构造VO类型分页结果
     *
     * @param pageable    分页器
     * @param voItemClass VO对象类
     * @return com.blcheung.cappuccino.vo.PagingResultVO<V>
     * @author BLCheung
     * @date 2022/1/24 10:21 下午
     */
    public static <D, V> PagingVO<V> resolve(Page<D> pageable, Class<V> voItemClass) {
        return new PagingVO<V>(pageable, voItemClass);
    }

    /**
     * 构造VO类型分页结果
     *
     * @param pageable 分页器
     * @param voItem   VO对象
     * @return com.blcheung.cappuccino.vo.PagingResultVO<V>
     * @author BLCheung
     * @date 2022/1/24 10:22 下午
     */
    public static <D, V> PagingVO<V> resolve(Page<D> pageable, V voItem) {
        return new PagingVO<V>(pageable, voItem);
    }

    /**
     * 构造VO类型集合的分页
     *
     * @param pageable
     * @param voList
     * @return com.blcheung.cappuccino.vo.PagingResultVO<V>
     * @author BLCheung
     * @date 2022/1/26 2:54 上午
     */
    public static <D, V> PagingVO<V> resolve(Page<D> pageable, List<V> voList) {
        return new PagingVO<V>(pageable, voList);
    }


    private static <T> Page<T> handleSorts(Page<T> page, SortPagingDTO dto) {
        if (StringUtils.isEmpty(dto.getProp())) return page;

        return page.addOrder(new OrderItem(dto.getProp(), dto.getOrder()
                                                             .equalsIgnoreCase("ascending")));
    }
}
