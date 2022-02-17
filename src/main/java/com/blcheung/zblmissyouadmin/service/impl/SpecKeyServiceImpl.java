package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.ParameterException;
import com.blcheung.zblmissyouadmin.dto.SpecKeyDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.SpecKeyMapper;
import com.blcheung.zblmissyouadmin.model.SpecKeyDO;
import com.blcheung.zblmissyouadmin.service.SpecKeyService;
import com.blcheung.zblmissyouadmin.vo.SpecKeyVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/14 12:44 上午
 */
@Service
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKeyDO> implements SpecKeyService {

    @Override
    public SpecKeyDO getSpecKey(Long specKeyId) {
        return this.lambdaQuery()
                   .eq(specKeyId != null, SpecKeyDO::getId, specKeyId)
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10601));
    }

    @Override
    public PagingVO<SpecKeyVO> getSpecKeyPage(BasePagingDTO dto) {
        Page<SpecKeyDO> pageable = PagingKit.build(dto, SpecKeyDO.class);
        pageable = this.lambdaQuery()
                       .page(pageable);

        return PagingKit.resolve(pageable, SpecKeyVO.class);
    }

    @Override
    public List<SpecKeyVO> getSpecKeysBySpuId(Long spuId) {
        if (spuId == null) throw new ParameterException("spuId参数错误");

        List<SpecKeyDO> specKeys = this.getBaseMapper()
                                       .getSpecKeysBySpuId(spuId);
        return ObjectUtils.isEmpty(specKeys)
                ? Collections.emptyList()
                : BeanKit.transformList(specKeys, SpecKeyVO.class);
    }

    @Transactional
    @Override
    public SpecKeyVO create(SpecKeyDTO dto) {
        SpecKeyDO newSpecKeyDO = BeanKit.transform(dto, SpecKeyDO.class);

        boolean isSaved = this.save(newSpecKeyDO);
        if (!isSaved) throw new FailedException(10602);

        return BeanKit.transform(newSpecKeyDO, SpecKeyVO.class);
    }

    @Transactional
    @Override
    public SpecKeyVO update(Long specKeyId, SpecKeyDTO dto) {
        SpecKeyDO specKeyDO = this.getSpecKey(specKeyId);
        SpecKeyDO newSpecKeyDO = BeanKit.transform(dto, specKeyDO);

        boolean isUpdated = this.updateById(newSpecKeyDO);
        if (!isUpdated) throw new FailedException(10603);

        return BeanKit.transform(newSpecKeyDO, new SpecKeyVO());
    }

    @Transactional
    @Override
    public Boolean delete(Long specKeyId) {
        SpecKeyDO specKeyDO = this.getSpecKey(specKeyId);
        return this.removeById(specKeyDO.getId());
    }
}
