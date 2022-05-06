package com.blcheung.cappuccino.service;

import com.blcheung.cappuccino.model.BannerItemDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-31
 */
public interface BannerItemService extends IService<BannerItemDO> {

    /**
     * 根据BannerId获取对应的BannerItems
     *
     * @param bannerId
     * @return java.util.List<com.blcheung.cappuccino.model.BannerItemDO>
     * @author BLCheung
     * @date 2022/1/31 11:29 下午
     */
    List<BannerItemDO> getItemsByBannerId(Long bannerId);

    /**
     * 根据BannerId删除对应的BannerItems
     *
     * @param bannerId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/1 12:46 上午
     */
    Boolean deleteItemsByBannerId(Long bannerId);

    /**
     * 获取对应Banner的BannerItem
     *
     * @param bannerId
     * @param bannerItemId
     * @return java.util.Optional<com.blcheung.cappuccino.model.BannerItemDO>
     * @author BLCheung
     * @date 2022/2/1 9:28 下午
     */
    Optional<BannerItemDO> getBannerItem(Long bannerId, Long bannerItemId);
}
