package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.ParameterException;
import com.blcheung.zblmissyouadmin.dto.SpecValueDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.mapper.SpecValueMapper;
import com.blcheung.zblmissyouadmin.model.SpecValueDO;
import com.blcheung.zblmissyouadmin.service.SpecValueService;
import com.blcheung.zblmissyouadmin.vo.SpecValueVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-15
 */
@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValueDO> implements SpecValueService {

    @Override
    public SpecValueDO getSpecValue(Long specValueId) {
        return this.lambdaQuery()
                   .eq(specValueId != null, SpecValueDO::getId, specValueId)
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10626));
    }

    @Override
    public List<SpecValueVO> getSpecValuesBySpecKey(Long specKeyId) {
        if (specKeyId == null) throw new ParameterException("规格名id错误");

        List<SpecValueDO> specValues = this.lambdaQuery()
                                           .eq(SpecValueDO::getSpecId, specKeyId)
                                           .list();

        return ObjectUtils.isEmpty(specValues)
                ? Collections.emptyList()
                : BeanKit.transformList(specValues, SpecValueVO.class);
    }

    @Transactional
    @Override
    public SpecValueVO create(SpecValueDTO dto) {
        SpecValueDO newSpecValueDO = BeanKit.transform(dto, new SpecValueDO());

        boolean isSaved = this.save(newSpecValueDO);
        if (!isSaved) throw new FailedException(10627);

        return BeanKit.transform(newSpecValueDO, SpecValueVO.class);
    }

    @Transactional
    @Override
    public SpecValueVO update(Long specValueId, SpecValueDTO dto) {
        SpecValueDO specValueDO = this.getSpecValue(specValueId);
        SpecValueDO newSpecValueDO = BeanKit.transform(dto, specValueDO);

        boolean isUpdated = this.updateById(newSpecValueDO);
        if (!isUpdated) throw new FailedException(10628);

        return BeanKit.transform(newSpecValueDO, new SpecValueVO());
    }

    @Transactional
    @Override
    public Boolean delete(Long specValueId) {
        SpecValueDO specValueDO = this.getSpecValue(specValueId);
        return this.removeById(specValueDO.getId());
    }
}
