package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.CreateBannerDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.vo.BannerVO;
import com.blcheung.zblmissyouadmin.vo.PagingVO;

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
     * 创建banner
     *
     * @param dto
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/30 8:44 下午
     */
    Boolean createBanner(CreateBannerDTO dto);

    /**
     * 获取Banner分页
     *
     * @param dto
     * @author BLCheung
     * @date 2022/1/30 9:13 下午
     * @return
     */
    PagingVO<BannerVO> getBannerPage(BasePagingDTO dto);
}
