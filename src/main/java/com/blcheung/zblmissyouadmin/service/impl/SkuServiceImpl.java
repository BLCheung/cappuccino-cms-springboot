package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.SkuMapper;
import com.blcheung.zblmissyouadmin.model.SkuDO;
import com.blcheung.zblmissyouadmin.service.SkuService;
import com.blcheung.zblmissyouadmin.vo.SkuVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuDO> implements SkuService {

    @Override
    public PagingVO<SkuVO> getSkuPage(BasePagingDTO dto) {
        Page<SkuDO> pageable = PagingKit.build(dto, SkuDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, new SkuVO());
    }
}
