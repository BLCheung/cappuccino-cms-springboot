package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.mapper.CmsUserGroupMapper;
import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.blcheung.zblmissyouadmin.service.CmsUserGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsUserGroupServiceImpl extends ServiceImpl<CmsUserGroupMapper, CmsUserGroupDO>
        implements CmsUserGroupService {

    @Override
    public List<Long> getGroupAllUserIds(Long groupId) {
        List<CmsUserGroupDO> groupAllUserIdRelations = this.lambdaQuery()
                                                           .eq(CmsUserGroupDO::getGroupId, groupId)
                                                           .list();

        return groupAllUserIdRelations.stream()
                                      .map(CmsUserGroupDO::getUserId)
                                      .collect(Collectors.toList());
    }

    @Override
    public List<Long> getUserAllGroupIds(Long userId) {
        List<CmsUserGroupDO> userAllGroupIdRelations = this.lambdaQuery()
                                                           .eq(CmsUserGroupDO::getUserId, userId)
                                                           .list();
        return userAllGroupIdRelations.stream()
                                      .map(CmsUserGroupDO::getGroupId)
                                      .collect(Collectors.toList());
    }

    @Override
    public CmsUserGroupDO getUserGroupRelation(Long userId, Long groupId) {
        return this.lambdaQuery()
                   .eq(CmsUserGroupDO::getUserId, userId)
                   .eq(CmsUserGroupDO::getGroupId, groupId)
                   .one();
    }

    @Override
    public Boolean addUserGroupRelations(Long userId, List<Long> addGroupIds) {
        if (addGroupIds.isEmpty()) return true;

        List<CmsUserGroupDO> relations = addGroupIds.stream()
                                                    .map(groupId -> new CmsUserGroupDO(userId, groupId))
                                                    .collect(Collectors.toList());

        return this.getBaseMapper()
                   .saveBatch(relations) > 0;
    }

    @Override
    public Boolean removeUserGroupByUserId(Long userId) {
        return this.getBaseMapper()
                   .deleteByUserId(userId) > 0;
    }

    @Override
    public Boolean removeUserGroupRelations(Long userId, List<Long> removeGroupIds) {
        if (removeGroupIds.isEmpty()) return true;

        return this.getBaseMapper()
                   .deleteBatch(userId, removeGroupIds) > 0;
    }
}
