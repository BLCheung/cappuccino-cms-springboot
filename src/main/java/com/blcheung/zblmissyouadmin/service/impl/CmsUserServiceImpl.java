package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.ParameterException;
import com.blcheung.zblmissyouadmin.common.exceptions.UnAuthorizedException;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.dto.cms.LoginDTO;
import com.blcheung.zblmissyouadmin.dto.cms.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.kit.UserKit;
import com.blcheung.zblmissyouadmin.mapper.CmsUserMapper;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.model.CmsUserGroupDO;
import com.blcheung.zblmissyouadmin.service.*;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private CmsPermissionService cmsPermissionService;

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
            Long guestGroupId = this.cmsGroupService.getGroupIdByLevel(GroupLevel.GUEST);
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
    public Tokens login(LoginDTO loginDTO) {
        CmsUserDO cmsUserDO = this.getUserByUserName(loginDTO.getUsername())
                                  .orElseThrow(() -> new NotFoundException(10103));
        Boolean isValid = this.cmsUserIdentityService.verifyUserNamePasswordIdentity(cmsUserDO.getId(),
                                                                                     cmsUserDO.getUsername(),
                                                                                     loginDTO.getPassword());
        if (!isValid) throw new ParameterException(10121);

        return this.cmsUserIdentityService.generateDoubleJwtToken(cmsUserDO.getId());
    }

    @Override
    public Boolean checkUserExistByUserName(String username) {
        return this.lambdaQuery()
                   .select(CmsUserDO::getId)
                   .eq(CmsUserDO::getUsername, username)
                   .exists();
    }

    @Override
    public Boolean checkUserExistByEmail(String email) {
        return this.lambdaQuery()
                   .select(CmsUserDO::getId)
                   .eq(CmsUserDO::getEmail, email)
                   .exists();
    }

    @Override
    public Optional<CmsUserDO> getUserByUserId(Long userId) {
        return this.lambdaQuery()
                   .eq(CmsUserDO::getId, userId)
                   .oneOpt();
    }

    @Override
    public Optional<CmsUserDO> getUserByUserName(String userName) {
        return this.lambdaQuery()
                   .eq(CmsUserDO::getUsername, userName)
                   .oneOpt();
    }

    @Override
    public Boolean checkUserIsAdmin(Long userId) {
        List<Long> userAllGroupIds = this.cmsUserGroupService.getUserAllGroupIds(userId);
        if (userAllGroupIds.isEmpty()) return false;

        List<Long> adminLevelGroupIds = this.cmsGroupService.getAdminLevelGroupsIds();
        return CommonUtil.isContainAnyIds(userAllGroupIds, adminLevelGroupIds);
    }

    @Override
    public Boolean checkUserIsRoot(Long userId) {
        Long rootGroupId = this.cmsGroupService.getRootGroupId();
        CmsUserGroupDO userGroupRelation = this.cmsUserGroupService.getUserGroupRelation(userId, rootGroupId);
        return userGroupRelation != null;
    }

    @Override
    public List<CmsPermissionDO> getUserPermissions(Long uid) {
        List<Long> userAllGroupIds = this.cmsUserGroupService.getUserAllGroupIds(uid);
        if (userAllGroupIds.isEmpty()) return Collections.emptyList();

        return this.cmsPermissionService.getPermissionsByGroupIds(userAllGroupIds);
    }

    @Override
    public Page<CmsUserDO> getUserPageByGroupLevel(Page<CmsUserDO> page, GroupLevel level) {
        return this.getBaseMapper()
                   .getUserPageByGroupLevel(page, level.getValue());
    }

    @Override
    public Page<CmsUserDO> getUserPageByRoot(Page<CmsUserDO> page) {
        Long rootGroupId = this.cmsGroupService.getRootGroupId();
        CmsUserDO cmsUserDO = UserKit.getUser();
        CmsUserGroupDO userGroupRelation = this.cmsUserGroupService.getUserGroupRelation(cmsUserDO.getId(),
                                                                                         rootGroupId);
        if (ObjectUtils.isEmpty(userGroupRelation)) throw new UnAuthorizedException();

        return this.lambdaQuery()
                   .ne(CmsUserDO::getId, userGroupRelation.getUserId())
                   .page(page);
    }

    @Override
    public Page<CmsUserDO> getAdminPage(Page<CmsUserDO> page) {
        return this.getUserPageByGroupLevel(page, GroupLevel.ADMIN);
    }

    @Override
    public Page<CmsUserDO> getUserPageByGroupId(Page<CmsUserDO> page, Long groupId) {
        return this.getBaseMapper()
                   .getUserPageByGroupId(page, groupId);
    }

    private void checkGroupExist(List<Long> groupIds) {
        boolean isGroupExist = this.cmsGroupService.checkGroupExistByIds(groupIds);
        if (!isGroupExist) throw new NotFoundException(10202);
    }

    private void checkGroupValidate(List<Long> groupIds) {
        List<Long> adminLevelGroupIds = this.cmsGroupService.getAdminLevelGroupsIds();

        boolean isContainAdminLevel = CommonUtil.isContainAnyIds(groupIds, adminLevelGroupIds);
        if (isContainAdminLevel) throw new ForbiddenException(10203);
    }
}
