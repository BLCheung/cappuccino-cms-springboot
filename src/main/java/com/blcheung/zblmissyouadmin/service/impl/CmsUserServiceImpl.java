package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.dto.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.mapper.CmsUserMapper;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.blcheung.zblmissyouadmin.service.CmsGroupService;
import com.blcheung.zblmissyouadmin.service.CmsUserGroupService;
import com.blcheung.zblmissyouadmin.service.CmsUserIdentityService;
import com.blcheung.zblmissyouadmin.service.CmsUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
public class CmsUserServiceImpl extends ServiceImpl<CmsUserMapper, CmsUserDO> implements CmsUserService {
    @Autowired
    private CmsGroupService cmsGroupService;

    @Autowired
    private CmsUserGroupService cmsUserGroupService;

    @Autowired
    private CmsUserIdentityService cmsUserIdentityService;

    @Transactional
    @Override
    public CmsUserDO registerUser(RegisterUserDTO dto) {
        Boolean isUserNameExist = this.checkUserExistByUserName(dto.getUsername());
        if (isUserNameExist) throw new ForbiddenException(10101);

        Boolean isEmailExist = this.checkUserExistByEmail(dto.getEmail());
        if (isEmailExist) throw new ForbiddenException(10102);

        CmsUserDO userDO = new CmsUserDO();
        BeanUtils.copyProperties(dto, userDO);
        // 保存用户
        this.baseMapper.insert(userDO);

        // 是否设置了分组
        if (ObjectUtils.isEmpty(dto.getGroupIds()) || dto.getGroupIds()
                                                         .isEmpty()) {
            // 没有分配分组，默认存储进游客组
            Long guestGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.GUEST);
            CmsUserGroupDO userGroupRelation = CmsUserGroupDO.builder()
                                                             .userId(userDO.getId())
                                                             .groupId(guestGroupId)
                                                             .build();
            this.cmsUserGroupService.save(userGroupRelation);
        } else {
            // 校验分组合法性
            this.checkGroupExist(dto.getGroupIds());
            this.checkGroupValidate(dto.getGroupIds());
            List<CmsUserGroupDO> userGroupRelations = dto.getGroupIds()

                                                         .stream()
                                                         .map(groupId -> CmsUserGroupDO.builder()
                                                                                       .userId(userDO.getId())
                                                                                       .groupId(groupId)
                                                                                       .build())
                                                         .collect(Collectors.toList());
            this.cmsUserGroupService.saveBatch(userGroupRelations);
        }

        // 创建用户凭证
        this.cmsUserIdentityService.createUsernamePasswordIdentity(userDO.getId(), userDO.getUsername(),
                                                                   dto.getPassword());

        return userDO;
    }

    @Override
    public Boolean checkUserExistByUserName(String username) {
        return this.lambdaQuery()
                   .eq(CmsUserDO::getUsername, username)
                   .exists();
    }

    @Override
    public Boolean checkUserExistByEmail(String email) {
        return this.lambdaQuery()
                   .eq(CmsUserDO::getEmail, email)
                   .exists();
    }


    private void checkGroupExist(List<Long> groupIds) {
        boolean isGroupExist = this.cmsGroupService.checkGroupExistByIds(groupIds);
        if (!isGroupExist) throw new NotFoundException(10202);
    }

    private void checkGroupValidate(List<Long> groupIds) {
        Long rootGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.ROOT);
        Long adminGroupId = this.cmsGroupService.getGroupIdByEnum(GroupLevel.ADMIN);

        boolean anyMatch = groupIds.stream()
                                   .anyMatch(groupId -> groupId.equals(rootGroupId) || groupId.equals(adminGroupId));

        if (anyMatch) throw new ForbiddenException(10203);
    }
}
