package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.BannerDTO;
import com.blcheung.zblmissyouadmin.dto.BannerItemDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.vo.BannerItemVO;
import com.blcheung.zblmissyouadmin.vo.BannerVO;
import com.blcheung.zblmissyouadmin.vo.BannerWithItemsVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-30
 */
public interface BannerService extends IService<BannerDO> {

    /**
     * 根据bannerId获取Banner
     *
     * @param bannerId
     * @return java.util.Optional<com.blcheung.zblmissyouadmin.model.BannerDO>
     * @author BLCheung
     * @date 2022/2/1 9:06 下午
     */
    Optional<BannerDO> getBannerById(Long bannerId);

    /**
     * 创建banner
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/30 8:44 下午
     */
    Boolean createBanner(BannerDTO dto);

    /**
     * 更新Banner
     *
     * @param bannerId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.BannerVO
     * @author BLCheung
     * @date 2022/1/31 11:03 下午
     */
    BannerVO updateBanner(Long bannerId, BannerDTO dto);

    /**
     * 删除Banner
     *
     * @param bannerId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/1 12:45 上午
     */
    Boolean deleteBanner(Long bannerId);

    /**
     * 获取Banner详情
     *
     * @param bannerId
     * @return com.blcheung.zblmissyouadmin.vo.BannerWithItemsVO
     * @author BLCheung
     * @date 2022/1/31 11:27 下午
     */
    BannerWithItemsVO getBanner(Long bannerId);

    /**
     * 获取Banner分页
     *
     * @param dto
     * @return
     * @author BLCheung
     * @date 2022/1/30 9:13 下午
     */
    PagingVO<BannerVO> getBannerPage(BasePagingDTO dto);

    /**
     * Banner创建Item
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.BannerItemVO
     * @author BLCheung
     * @date 2022/2/1 9:20 下午
     */
    BannerItemVO createItem(BannerItemDTO dto);

    /**
     * Banner更新Item
     *
     * @param itemId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.BannerItemVO
     * @author BLCheung
     * @date 2022/2/1 9:24 下午
     */
    BannerItemVO updateItem(Long itemId, BannerItemDTO dto);

    /**
     * Banner删除Item
     *
     * @param itemId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/1 9:47 下午
     */
    Boolean deleteItem(Long itemId);
}
