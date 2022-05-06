package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.dto.SpecKeyDTO;
import com.blcheung.cappuccino.dto.SpecSelectorDTO;
import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import com.blcheung.cappuccino.model.SpecKeyDO;
import com.blcheung.cappuccino.model.SpecKeyValueDO;
import com.blcheung.cappuccino.vo.SpecKeyVO;
import com.blcheung.cappuccino.vo.common.PagingVO;

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
     * @return com.blcheung.cappuccino.model.SpecKeyDO
     * @author BLCheung
     * @date 2022/2/17 8:44 下午
     */
    SpecKeyDO getSpecKey(Long specKeyId);

    /**
     * 获取规格名分页
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.SpecKeyVO>
     * @author BLCheung
     * @date 2022/2/16 11:20 下午
     */
    PagingVO<SpecKeyVO> getSpecKeyPage(BasePagingDTO dto);

    /**
     * 获取Spu下的所属规格名
     *
     * @param spuId
     * @return java.util.List<com.blcheung.cappuccino.vo.SpecKeyVO>
     * @author BLCheung
     * @date 2022/2/17 12:19 上午
     */
    List<SpecKeyVO> getSpecKeysBySpuId(Long spuId);

    /**
     * 创建规格名
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.SpecKeyVO
     * @author BLCheung
     * @date 2022/2/17 8:33 下午
     */
    SpecKeyVO create(SpecKeyDTO dto);

    /**
     * 更新规格名
     *
     * @param specKeyId
     * @param dto
     * @return com.blcheung.cappuccino.vo.SpecKeyVO
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

    /**
     * 获取所有所选的规格名与规格值
     *
     * @param specs
     * @return java.util.List<com.blcheung.cappuccino.model.SpecKeyValueDO>
     * @author BLCheung
     * @date 2022/2/18 10:19 下午
     */
    List<SpecKeyValueDO> getSpecValues(List<SpecSelectorDTO> specs);
}
