package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.model.BannerItemDO;
import com.blcheung.zblmissyouadmin.mapper.BannerItemMapper;
import com.blcheung.zblmissyouadmin.service.BannerItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-31
 */
@Service
public class BannerItemServiceImpl extends ServiceImpl<BannerItemMapper, BannerItemDO> implements BannerItemService {

    @Override
    public List<BannerItemDO> getItemsByBannerId(Long bannerId) {
        List<BannerItemDO> items = this.lambdaQuery()
                                       .eq(BannerItemDO::getBannerId, bannerId)
                                       .list();
        return ObjectUtils.isEmpty(items) ? Collections.emptyList() : items;
    }

    @Override
    public Boolean deleteItemsByBannerId(Long bannerId) {
        return this.lambdaUpdate()
                   .eq(!ObjectUtils.isEmpty(bannerId), BannerItemDO::getBannerId, bannerId)
                   .remove();
    }

    @Override
    public Optional<BannerItemDO> getBannerItem(Long bannerId, Long bannerItemId) {
        if (bannerId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(BannerItemDO::getBannerId, bannerId)
                   .eq(bannerItemId != null, BannerItemDO::getId, bannerItemId)
                   .oneOpt();
    }
}
