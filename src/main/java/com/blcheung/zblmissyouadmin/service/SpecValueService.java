package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.SpecValueDTO;
import com.blcheung.zblmissyouadmin.model.SpecValueDO;
import com.blcheung.zblmissyouadmin.vo.SpecValueVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-15
 */
public interface SpecValueService extends IService<SpecValueDO> {

    /**
     * 获取规格值
     *
     * @param specValueId
     * @return com.blcheung.zblmissyouadmin.model.SpecValueDO
     * @author BLCheung
     * @date 2022/2/17 8:23 下午
     */
    SpecValueDO getSpecValue(Long specValueId);

    /**
     * 获取指定规格名下所有规格值
     *
     * @param specKeyId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.SpecValueVO>
     * @author BLCheung
     * @date 2022/2/17 8:16 下午
     */
    List<SpecValueVO> getSpecValuesBySpecKey(Long specKeyId);

    /**
     * 创建规格值
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpecValueVO
     * @author BLCheung
     * @date 2022/2/17 8:17 下午
     */
    SpecValueVO create(SpecValueDTO dto);

    /**
     * 更新规格值
     *
     * @param specValueId
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.SpecValueVO
     * @author BLCheung
     * @date 2022/2/17 8:18 下午
     */
    SpecValueVO update(Long specValueId, SpecValueDTO dto);

    /**
     * 删除规格值
     *
     * @param specValueId
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/17 8:18 下午
     */
    Boolean delete(Long specValueId);
}
