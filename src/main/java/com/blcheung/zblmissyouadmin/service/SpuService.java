package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.SpuDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.SpuDO;
import com.blcheung.zblmissyouadmin.vo.SpuDetailVO;
import com.blcheung.zblmissyouadmin.vo.SpuVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
public interface SpuService extends IService<SpuDO> {

    /**
     * 该Spu是否存在
     *
     * @param spuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/14 7:52 下午
     */
    Boolean isSpuExist(Long spuId);

    /**
     * 获取Spu实体
     *
     * @param spuId
     * @return java.util.Optional<com.blcheung.zblmissyouadmin.model.SpuDO>
     * @author BLCheung
     * @date 2022/2/10 3:17 上午
     */
    Optional<SpuDO> getSpu(Long spuId);

    /**
     * 获取Spu分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.SpuVO>
     * @author BLCheung
     * @date 2022/2/10 3:04 上午
     */
    PagingVO<SpuVO> getSpuPage(BasePagingDTO dto);

    /**
     * 获取Spu详情视图数据
     *
     * @param spuId
     * @return com.blcheung.zblmissyouadmin.vo.SpuDetailVO
     * @author BLCheung
     * @date 2022/2/11 12:18 上午
     */
    SpuDetailVO getSpuDetail(Long spuId);

    /**
     * 创建Spu
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpuVO
     * @author BLCheung
     * @date 2022/2/14 12:10 上午
     */
    SpuVO create(SpuDTO dto);

    /**
     * 更新Spu
     *
     * @param spuId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpuVO
     * @author BLCheung
     * @date 2022/2/14 9:53 下午
     */
    SpuVO update(Long spuId, SpuDTO dto);

    /**
     * 删除SPU
     *
     * @param spuId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/15 1:17 上午
     */
    Boolean delete(Long spuId);
}
