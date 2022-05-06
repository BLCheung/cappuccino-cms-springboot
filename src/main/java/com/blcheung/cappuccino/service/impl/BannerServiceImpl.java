package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.BannerDTO;
import com.blcheung.cappuccino.dto.BannerItemDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.BannerMapper;
import com.blcheung.cappuccino.model.BannerDO;
import com.blcheung.cappuccino.model.BannerItemDO;
import com.blcheung.cappuccino.service.BannerItemService;
import com.blcheung.cappuccino.service.BannerService;
import com.blcheung.cappuccino.vo.BannerItemVO;
import com.blcheung.cappuccino.vo.BannerVO;
import com.blcheung.cappuccino.vo.BannerWithItemsVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-30
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDO> implements BannerService {

    @Autowired
    private BannerItemService bannerItemService;

    @Override
    public Optional<BannerDO> getBannerById(Long bannerId) {
        return this.lambdaQuery()
                   .eq(!ObjectUtils.isEmpty(bannerId), BannerDO::getId, bannerId)
                   .oneOpt();
    }

    @Transactional
    @Override
    public Boolean createBanner(BannerDTO dto) {
        BannerDO newBannerDO = BeanKit.transform(dto, new BannerDO());
        return this.save(newBannerDO);
    }

    @Transactional
    @Override
    public BannerVO updateBanner(Long bannerId, BannerDTO dto) {
        BannerDO bannerDO = this.getBannerById(bannerId)
                                .orElseThrow(() -> new NotFoundException(10301));

        BannerDO updateBannerDO = BeanKit.transform(dto, bannerDO);

        boolean isUpdate = this.updateById(updateBannerDO);
        if (!isUpdate) throw new FailedException(10302);

        return BeanKit.transform(updateBannerDO, new BannerVO());
    }

    @Transactional
    @Override
    public Boolean deleteBanner(Long bannerId) {
        BannerDO bannerDO = this.getBannerById(bannerId)
                                .orElseThrow(() -> new NotFoundException(10301));

        this.bannerItemService.deleteItemsByBannerId(bannerDO.getId());

        return this.removeById(bannerDO.getId());
    }

    @Override
    public BannerWithItemsVO getBanner(Long bannerId) {
        BannerDO bannerDO = this.getBannerById(bannerId)
                                .orElseThrow(() -> new NotFoundException(10301));

        List<BannerItemDO> items = this.bannerItemService.getItemsByBannerId(bannerDO.getId());
        List<BannerItemVO> bannerItemVOs = BeanKit.transformList(items, BannerItemVO.class);

        return BeanKit.transform(bannerDO, new BannerWithItemsVO(bannerItemVOs));
    }

    @Override
    public PagingVO<BannerVO> getBannerPage(BasePagingDTO dto) {
        Page<BannerDO> bannerPage = PagingKit.build(dto, BannerDO.class);
        bannerPage = this.lambdaQuery()
                         .page(bannerPage);

        return PagingKit.resolve(bannerPage, BannerVO.class);
    }

    @Transactional
    @Override
    public BannerItemVO createItem(BannerItemDTO dto) {
        this.getBannerById(dto.getBannerId())
            .orElseThrow(() -> new NotFoundException(10301));

        BannerItemDO newBannerItemDO = BeanKit.transform(dto, new BannerItemDO());

        boolean createSuccess = this.bannerItemService.save(newBannerItemDO);
        if (!createSuccess) throw new FailedException(10300);

        return BeanKit.transform(newBannerItemDO, new BannerItemVO());
    }

    @Transactional
    @Override
    public BannerItemVO updateItem(Long itemId, BannerItemDTO dto) {
        BannerDO bannerDO = this.getBannerById(dto.getBannerId())
                                .orElseThrow(() -> new NotFoundException(10301));

        BannerItemDO bannerItemDO = this.bannerItemService.getBannerItem(bannerDO.getId(), itemId)
                                                          .orElseThrow(() -> new NotFoundException(10303));

        BannerItemDO newBannerItemDO = BeanKit.transform(dto, bannerItemDO);

        boolean isUpdate = this.bannerItemService.updateById(newBannerItemDO);
        if (!isUpdate) throw new FailedException(10304);

        return BeanKit.transform(newBannerItemDO, new BannerItemVO());
    }

    @Transactional
    @Override
    public Boolean deleteItem(Long itemId) {
        BannerItemDO bannerItemDO = this.bannerItemService.getById(itemId);
        if (bannerItemDO == null) throw new NotFoundException(10303);

        return this.bannerItemService.removeById(bannerItemDO.getId());
    }
}
