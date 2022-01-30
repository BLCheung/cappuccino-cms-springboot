package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.dto.CreateBannerDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.BannerMapper;
import com.blcheung.zblmissyouadmin.model.BannerDO;
import com.blcheung.zblmissyouadmin.service.BannerService;
import com.blcheung.zblmissyouadmin.vo.BannerVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Boolean createBanner(CreateBannerDTO dto) {
        BannerDO newBannerDO = BeanKit.transform(dto, new BannerDO());
        return this.save(newBannerDO);
    }

    @Override
    public PagingVO<BannerVO> getBannerPage(BasePagingDTO dto) {
        Page<BannerDO> bannerPage = PagingKit.build(dto, BannerDO.class);
        bannerPage = this.lambdaQuery()
                         .page(bannerPage);

        return PagingKit.resolve(bannerPage, BannerVO.class);
    }
}
