package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.exceptions.FailedException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.ActivityDTO;
import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.PagingKit;
import com.blcheung.zblmissyouadmin.mapper.ActivityMapper;
import com.blcheung.zblmissyouadmin.model.ActivityDO;
import com.blcheung.zblmissyouadmin.service.ActivityService;
import com.blcheung.zblmissyouadmin.vo.ActivityVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-03
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityService {

    @Override
    public Optional<ActivityDO> getActivity(Long activityId) {
        if (activityId == null) return Optional.empty();
        return this.lambdaQuery()
                   .eq(ActivityDO::getId, activityId)
                   .oneOpt();
    }

    @Override
    public ActivityVO createActivity(ActivityDTO dto) {
        ActivityDO newActivityDO = BeanKit.transform(dto, new ActivityDO());

        boolean isSave = this.save(newActivityDO);
        if (!isSave) throw new FailedException(10352);

        return BeanKit.transform(newActivityDO, new ActivityVO());
    }

    @Override
    public ActivityVO updateActivity(Long activityId, ActivityDTO dto) {
        ActivityDO activityDO = this.getActivity(activityId)
                                    .orElseThrow(() -> new NotFoundException(10351));

        ActivityDO newActivityDO = BeanKit.transform(dto, activityDO);

        boolean isUpdated = this.updateById(newActivityDO);
        if (!isUpdated) throw new FailedException(10353);

        return BeanKit.transform(newActivityDO, new ActivityVO());
    }

    @Override
    public Boolean deleteActivity(Long activityId) {
        ActivityDO activityDO = this.getById(activityId);
        if (activityDO == null) throw new NotFoundException(10351);

        return this.removeById(activityId);
    }

    @Override
    public PagingVO<ActivityVO> getActivityPage(BasePagingDTO dto) {
        Page<ActivityDO> pageable = PagingKit.build(dto, ActivityDO.class);
        pageable = this.lambdaQuery()
                       .orderByDesc(ActivityDO::getCreateTime)
                       .page(pageable);
        return PagingKit.resolve(pageable, ActivityVO.class);
    }
}
