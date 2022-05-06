package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.dto.SkuDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.model.SkuDO;
import com.blcheung.cappuccino.vo.SkuVO;
import com.blcheung.cappuccino.vo.common.PagingVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
public interface SkuService extends IService<SkuDO> {

    /**
     * 获取Sku
     *
     * @param skuId
     * @return com.blcheung.cappuccino.model.SkuDO
     * @author BLCheung
     * @date 2022/2/19 2:09 上午
     */
    SkuDO getSku(Long skuId);

    /**
     * 检查Sku标题是否已存在
     *
     * @param skuTitle
     * @author BLCheung
     * @date 2022/2/18 8:43 下午
     */
    void checkSkuTitleExist(String skuTitle);

    /**
     * Sku分页
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.SkuVO>
     * @author BLCheung
     * @date 2022/2/15 5:39 下午
     */
    PagingVO<SkuVO> getSkuPage(BasePagingDTO dto);

    /**
     * 创建Sku
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.SkuVO
     * @author BLCheung
     * @date 2022/2/18 8:02 下午
     */
    SkuVO create(SkuDTO dto);

    /**
     * 更新Sku
     *
     * @param skuId
     * @param dto
     * @return com.blcheung.cappuccino.vo.SkuVO
     * @author BLCheung
     * @date 2022/2/19 2:03 上午
     */
    SkuVO update(Long skuId, SkuDTO dto);

    /**
     * 删除Sku
     *
     * @param skuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/19 2:48 上午
     */
    Boolean delete(Long skuId);
}
