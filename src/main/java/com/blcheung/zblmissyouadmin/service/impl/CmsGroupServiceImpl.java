package com.blcheung.zblmissyouadmin.service.impl;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.mapper.CmsGroupMapper;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
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
public class CmsGroupServiceImpl extends ServiceImpl<CmsGroupMapper, CmsGroupDO> implements CmsGroupService {

    @Override
    public boolean createGroup(CmsGroupDO groupDO) {
        return this.getBaseMapper()
                   .insert(groupDO) > 0;
    }

    @Override
    public boolean checkGroupExistById(Long id) {
        return this.lambdaQuery()
                   .eq(CmsGroupDO::getId, id)
                   .exists();
    }

    @Override
    public boolean checkGroupExistByIds(List<Long> ids) {
        return this.lambdaQuery()
                   .in(CmsGroupDO::getId, ids)
                   .exists();
    }

    @Override
    public boolean checkGroupExistByName(String name) {
        return this.lambdaQuery()
                   .eq(CmsGroupDO::getName, name)
                   .exists();
    }

    @Override
    public Long getGroupIdByEnum(GroupLevel groupLevel) {
        // 普通用户组无需返回具体分组id
        if (GroupLevel.USER.equals(groupLevel)) return 0L;
        CmsGroupDO cmsGroupDO = this.lambdaQuery()
                                    .eq(CmsGroupDO::getLevel, groupLevel.getValue())
                                    .one();
        return cmsGroupDO.getId();
    }

    @Override
    public void validateGroupNameExist(String groupName) {
        boolean exist = this.checkGroupExistByName(groupName);
        if (exist) throw new ForbiddenException(10201);
    }

}
