package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.mapper.CmsGroupMapper;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
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
                   .select(CmsGroupDO::getName)
                   .eq(StringUtils.hasText(name), CmsGroupDO::getName, name)
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
    public List<Long> getAdminLevelGroups() {
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
        return this.getGroupIdByEnum(GroupLevel.ROOT);
    }

    @Override
    public List<CmsGroupDO> getAllUserLevelGroups() {
        List<Long> adminLevelGroupsIds = this.getAdminLevelGroups();
        return this.lambdaQuery()
                   .notIn(CmsGroupDO::getId, adminLevelGroupsIds)
                   .list();
    }
}
