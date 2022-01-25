package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.mapper.CmsGroupMapper;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
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
public class CmsGroupServiceImpl extends ServiceImpl<CmsGroupMapper, CmsGroupDO> implements CmsGroupService {

    @Override
    public boolean createGroup(CmsGroupDO groupDO) {
        return this.getBaseMapper()
                   .insert(groupDO) > 0;
    }

    @Override
    public boolean checkGroupExistById(Long id) {
        return this.lambdaQuery()
                   .select(CmsGroupDO::getId)
                   .eq(!ObjectUtils.isEmpty(id), CmsGroupDO::getId, id)
                   .exists();
    }

    @Override
    public boolean checkGroupExistByIds(List<Long> ids) {
        return this.lambdaQuery()
                   .select(CmsGroupDO::getId)
                   .in(!ids.isEmpty(), CmsGroupDO::getId, ids)
                   .exists();
    }

    @Override
    public boolean checkGroupExistByName(String name) {
        return this.lambdaQuery()
                   .select(CmsGroupDO::getId)
                   .eq(StringUtils.hasText(name), CmsGroupDO::getName, name)
                   .exists();
    }

    @Override
    public Long getGroupIdByLevel(GroupLevel groupLevel) {
        // 普通用户组无需返回具体分组id
        if (GroupLevel.USER.equals(groupLevel)) return 0L;
        CmsGroupDO cmsGroupDO = this.lambdaQuery()
                                    .select(CmsGroupDO::getId)
                                    .eq(CmsGroupDO::getLevel, groupLevel.getValue())
                                    .one();
        return cmsGroupDO.getId();
    }

    @Override
    public List<CmsGroupDO> getGroupsByLevelEQ(GroupLevel level) {
        return this.lambdaQuery()
                   .eq(CmsGroupDO::getLevel, level.getValue())
                   .list();
    }

    @Override
    public List<CmsGroupDO> getGroupsByLevelGE(GroupLevel level) {
        return this.lambdaQuery()
                   .ge(CmsGroupDO::getLevel, level.getValue())
                   .orderByAsc(CmsGroupDO::getLevel)
                   .list();
    }

    @Override
    public void validateGroupNameExist(String groupName) {
        boolean exist = this.checkGroupExistByName(groupName);
        if (exist) throw new ForbiddenException(10201);
    }

    @Override
    public void validateGroupIdExist(Long groupId) {
        boolean exist = this.checkGroupExistById(groupId);
        if (!exist) throw new NotFoundException(10208);
    }

    @Override
    public List<Long> getUserGroupIds(Long userId) {
        List<Long> userGroupIds = this.getBaseMapper()
                                      .getGroupIdsByUserId(userId);
        return userGroupIds.isEmpty() ? Collections.emptyList() : userGroupIds;
    }

    @Override
    public List<CmsGroupDO> getUserGroups(Long userId) {
        List<CmsGroupDO> userGroups = this.getBaseMapper()
                                          .getGroupsByUserId(userId);
        return userGroups.isEmpty() ? Collections.emptyList() : userGroups;
    }

    @Override
    public List<Long> getAdminLevelGroupsIds() {
        return this.lambdaQuery()
                   .select(CmsGroupDO::getId)   // 只查id即可，返回的对象内只包含id字段以及基类的字段（不查则为null）
                   .le(CmsGroupDO::getLevel, GroupLevel.ADMIN)
                   .list()
                   .stream()
                   .map(CmsGroupDO::getId)
                   .collect(Collectors.toList());
    }

    @Override
    public Long getRootGroupId() {
        return this.getGroupIdByLevel(GroupLevel.ROOT);
    }

    @Override
    public List<CmsGroupDO> getAllUserLevelGroups() {
        return this.getGroupsByLevelGE(GroupLevel.USER);
    }

    @Override
    public Boolean validateGroupIdExistBatch(List<Long> groupIds) {
        Long existCount = this.lambdaQuery()
                              .select(CmsGroupDO::getId)
                              .in(CmsGroupDO::getId, groupIds)
                              .count();
        return existCount == groupIds.size();
    }

    @Override
    public void validateIsUserLevelGroupId(Long groupId) {
        List<Long> adminLevelGroupsIds = this.getAdminLevelGroupsIds();
        if (CommonUtil.isContainOneId(groupId, adminLevelGroupsIds)) {
            throw new ForbiddenException(10211);
        }
    }

    @Override
    public void validateIsRootLevelGroupId(Long groupId) {
        Long rootGroupId = this.getRootGroupId();
        if (!rootGroupId.equals(groupId)) {
            throw new ForbiddenException(10222);
        }
    }

    @Override
    public void validateIsAdminLevelGroupId(Long groupId) {
        List<Long> adminLevelGroupsIds = this.getAdminLevelGroupsIds();
        if (!CommonUtil.isContainOneId(groupId, adminLevelGroupsIds)) {
            throw new ForbiddenException(10223);
        }
    }
}
