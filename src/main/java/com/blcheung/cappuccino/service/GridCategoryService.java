package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.dto.GridCategoryDTO;
import com.blcheung.cappuccino.model.GridCategoryDO;
import com.blcheung.cappuccino.vo.GridCategoryVO;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-06
 */
public interface GridCategoryService extends IService<GridCategoryDO> {

    /**
     * 获取宫格
     *
     * @param gridId
     * @return java.util.Optional<com.blcheung.cappuccino.model.GridCategoryDO>
     * @author BLCheung
     * @date 2022/2/6 11:08 下午
     */
    Optional<GridCategoryDO> getGridCategory(Long gridId);

    /**
     * 创建宫格
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.GridCategoryVO
     * @author BLCheung
     * @date 2022/2/6 10:57 下午
     */
    GridCategoryVO createGirdCategory(GridCategoryDTO dto);

    /**
     * 更新宫格
     *
     * @param id
     * @param dto
     * @return com.blcheung.cappuccino.vo.GridCategoryVO
     * @author BLCheung
     * @date 2022/2/7 1:49 上午
     */
    GridCategoryVO updateGridCategory(Long id, GridCategoryDTO dto);

    /**
     * 删除宫格
     *
     * @param id
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/7 2:01 上午
     */
    Boolean deleteGridCategory(Long id);

    /**
     * 获取所有宫格数据
     *
     * @return java.util.List<com.blcheung.cappuccino.vo.GridCategoryVO>
     * @author BLCheung
     * @date 2022/2/7 2:05 上午
     */
    List<GridCategoryVO> getAllGridCategory();
}
