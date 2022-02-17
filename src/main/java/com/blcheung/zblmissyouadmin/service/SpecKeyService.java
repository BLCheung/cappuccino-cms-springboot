package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.SpecKeyDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.model.SpecKeyDO;
import com.blcheung.zblmissyouadmin.vo.SpecKeyVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/14 12:37 上午
 */
public interface SpecKeyService extends IService<SpecKeyDO> {

    /**
     * 获取规格名
     *
     * @param specKeyId
     * @return com.blcheung.zblmissyouadmin.model.SpecKeyDO
     * @author BLCheung
     * @date 2022/2/17 8:44 下午
     */
    SpecKeyDO getSpecKey(Long specKeyId);

    /**
     * 获取规格名分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.SpecKeyVO>
     * @author BLCheung
     * @date 2022/2/16 11:20 下午
     */
    PagingVO<SpecKeyVO> getSpecKeyPage(BasePagingDTO dto);

    /**
     * 获取Spu下的所属规格名
     *
     * @param spuId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.SpecKeyVO>
     * @author BLCheung
     * @date 2022/2/17 12:19 上午
     */
    List<SpecKeyVO> getSpecKeysBySpuId(Long spuId);

    /**
     * 创建规格名
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpecKeyVO
     * @author BLCheung
     * @date 2022/2/17 8:33 下午
     */
    SpecKeyVO create(SpecKeyDTO dto);

    /**
     * 更新规格名
     *
     * @param specKeyId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpecKeyVO
     * @author BLCheung
     * @date 2022/2/17 8:36 下午
     */
    SpecKeyVO update(Long specKeyId, SpecKeyDTO dto);

    /**
     * 删除规格名
     *
     * @param specKeyId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/17 8:36 下午
     */
    Boolean delete(Long specKeyId);
}
