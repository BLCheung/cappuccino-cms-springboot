package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.mapper.CmsPermissionMapper;
import com.blcheung.zblmissyouadmin.service.CmsPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsPermissionServiceImpl extends ServiceImpl<CmsPermissionMapper, CmsPermissionDO>
        implements CmsPermissionService {

    @Override
    public Boolean checkPermissionExist(Long id) {
        return this.lambdaQuery()
                   .eq(CmsPermissionDO::getId, id)
                   .exists();
    }

    @Override
    public Boolean checkPermissionExistBatch(List<Long> ids) {
        return this.lambdaQuery()
                   .in(CmsPermissionDO::getId, ids)
                   .exists();
    }
}
