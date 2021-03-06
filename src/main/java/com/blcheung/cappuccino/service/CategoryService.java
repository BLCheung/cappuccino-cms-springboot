package com.blcheung.cappuccino.service;

import com.blcheung.cappuccino.dto.CategoryDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.model.CategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.vo.CategoryVO;
import com.blcheung.cappuccino.vo.RootCategoryVO;
import com.blcheung.cappuccino.vo.common.PagingVO;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-04
 */
public interface CategoryService extends IService<CategoryDO> {

    /**
     * 获取分类
     *
     * @param categoryId
     * @return java.util.Optional<com.blcheung.cappuccino.model.CategoryDO>
     * @author BLCheung
     * @date 2022/2/5 12:18 上午
     */
    Optional<CategoryDO> getCategory(Long categoryId);

    /**
     * 获取所有分类树结构
     *
     * @return java.util.List<com.blcheung.cappuccino.vo.RootCategoryVO>
     * @author BLCheung
     * @date 2022/2/22 12:12 上午
     */
    List<RootCategoryVO> getAllCategoryTree();

    /**
     * 创建分类
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.CategoryVO
     * @author BLCheung
     * @date 2022/2/5 12:09 上午
     */
    CategoryVO createCategory(CategoryDTO dto);

    /**
     * 更新分类
     *
     * @param categoryId
     * @param dto
     * @return com.blcheung.cappuccino.vo.CategoryVO
     * @author BLCheung
     * @date 2022/2/5 12:17 上午
     */
    CategoryVO updateCategory(Long categoryId, CategoryDTO dto);

    /**
     * 删除分类
     *
     * @param categoryId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/5 12:24 上午
     */
    Boolean deleteCategory(Long categoryId);

    /**
     * 获取分类分页（父分类）
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.CategoryVO>
     * @author BLCheung
     * @date 2022/2/5 12:31 上午
     */
    PagingVO<CategoryVO> getCategoryPage(BasePagingDTO dto);

    /**
     * 获取指定父分类下所有子分类
     *
     * @param parentId
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.CategoryVO>
     * @author BLCheung
     * @date 2022/2/5 12:53 上午
     */
    PagingVO<CategoryVO> getSubCategoryPage(Long parentId, BasePagingDTO dto);

    /**
     * 该父分类下是否存在子分类
     *
     * @param parentId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/5 1:47 上午
     */
    Boolean hasSubCategory(Long parentId);

    /**
     * 获取某张优惠券下的适用分类
     *
     * @param couponId
     * @return java.util.List<com.blcheung.cappuccino.model.CategoryDO>
     * @author BLCheung
     * @date 2022/2/22 4:47 上午
     */
    List<CategoryDO> getCategoriesByCouponId(Long couponId);
}
