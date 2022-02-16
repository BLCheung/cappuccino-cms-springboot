package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.SkuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.vo.SkuVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

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
     * Sku分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.SkuVO>
     * @author BLCheung
     * @date 2022/2/15 5:39 下午
     */
    PagingVO<SkuVO> getSkuPage(BasePagingDTO dto);
}
