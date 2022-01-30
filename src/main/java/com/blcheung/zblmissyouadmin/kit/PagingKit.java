package com.blcheung.zblmissyouadmin.kit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

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
     * 构造数据源分页结果
     *
     * @param pageable 分页器
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<T>
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
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<V>
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
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<V>
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
     * @return com.blcheung.zblmissyouadmin.vo.PagingResultVO<V>
     * @author BLCheung
     * @date 2022/1/26 2:54 上午
     */
    public static <D, V> PagingVO<V> resolve(Page<D> pageable, List<V> voList) {
        return new PagingVO<V>(pageable, voList);
    }
}
