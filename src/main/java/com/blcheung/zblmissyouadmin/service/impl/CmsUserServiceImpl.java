package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.common.exceptions.DatabaseActionException;
import com.blcheung.zblmissyouadmin.common.exceptions.ForbiddenException;
import com.blcheung.zblmissyouadmin.common.exceptions.NotFoundException;
import com.blcheung.zblmissyouadmin.common.exceptions.UnAuthorizedException;
import com.blcheung.zblmissyouadmin.common.token.DoubleJWT;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.dto.cms.LoginDTO;
import com.blcheung.zblmissyouadmin.dto.cms.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.dto.cms.UpdateUserInfoDTO;
import com.blcheung.zblmissyouadmin.dto.cms.UpdateUserPasswordDTO;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.kit.UserKit;
import com.blcheung.zblmissyouadmin.mapper.CmsUserMapper;
import com.blcheung.zblmissyouadmin.model.*;
import com.blcheung.zblmissyouadmin.service.*;
import com.blcheung.zblmissyouadmin.util.CommonUtil;
import com.blcheung.zblmissyouadmin.util.EncryptUtil;
import com.blcheung.zblmissyouadmin.vo.cms.PermissionModuleVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserInfoVO;
import com.blcheung.zblmissyouadmin.vo.cms.UserPermissionsVO;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private DoubleJWT jwt;

    @Transactional
    @Override
    public CmsUserDO registerUser(RegisterUserDTO dto) {
        this.validateUserNameExist(dto.getUsername());

        this.validateEmailExist(dto.getEmail());

        CmsUserDO userDO = new CmsUserDO();
        BeanUtils.copyProperties(dto, userDO);
        // 保存用户
        this.baseMapper.insert(userDO);

        // 是否设置了分组
        if (ObjectUtils.isEmpty(dto.getGroupIds())) {
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
        this.cmsUserIdentityService.verifyUserNamePasswordIdentity(cmsUserDO.getId(), cmsUserDO.getUsername(),
                                                                   loginDTO.getPassword())
                                   .orElseThrow(() -> new ForbiddenException(10121));

        return this.cmsUserIdentityService.generateDoubleJwtToken(cmsUserDO.getId());
    }

    @Transactional
    @Override
    public Optional<CmsUserDO> update(UpdateUserInfoDTO dto) {
        CmsUserDO currentUser = UserKit.getUser();

        boolean isUserNameNotEqual = !StringUtils.isBlank(dto.getUsername()) &&
                                     !StringUtils.equals(currentUser.getUsername(), dto.getUsername());
        if (isUserNameNotEqual) {
            this.validateUserNameExist(dto.getUsername());
        }

        boolean updateUserSuccess = this.lambdaUpdate()
                                        .eq(CmsUserDO::getId, currentUser.getId())
                                        .set(isUserNameNotEqual, CmsUserDO::getUsername, dto.getUsername())
                                        .set(CmsUserDO::getNickname, dto.getNickname())
                                        .set(CmsUserDO::getEmail, dto.getEmail())
                                        .set(!StringUtils.isBlank(dto.getAvatar()), CmsUserDO::getAvatar,
                                             dto.getAvatar())
                                        .update();

        if (!updateUserSuccess) throw new DatabaseActionException(10104);

        if (isUserNameNotEqual) {
            Boolean updateIdentitySuccess = this.cmsUserIdentityService.changeUserNameIdentity(currentUser.getId(),
                                                                                               dto.getUsername());
            if (!updateIdentitySuccess) throw new DatabaseActionException(10120);
        }

        return this.getUserByUserId(currentUser.getId());
    }

    @Transactional
    @Override
    public Boolean changePassword(UpdateUserPasswordDTO dto) {
        CmsUserDO currentUser = UserKit.getUser();
        CmsUserIdentityDO currentUserIdentity = this.cmsUserIdentityService.verifyUserNamePasswordIdentity(
                currentUser.getId(), currentUser.getUsername(), dto.getPassword())
                                                                           .orElseThrow(
                                                                                   () -> new ForbiddenException(10122));
        // 校验新旧密码是否一致
        boolean isSameCredential = EncryptUtil.decrypt(currentUserIdentity.getCredential(), dto.getNewPassword());
        if (isSameCredential) throw new ForbiddenException(10123);

        return this.cmsUserIdentityService.changeUserPasswordIdentity(currentUser.getId(), dto.getNewPassword());
    }

    @Override
    public UserPermissionsVO getUserPermissions() {
        CmsUserDO currentUser = UserKit.getUser();
        List<CmsPermissionDO> userPermissions = this.getUserPermissions(currentUser.getId());
        GroupLevel groupLevel = this.cmsGroupService.getUserGroupLevel(currentUser.getId());

        List<PermissionModuleVO> modules = this.cmsPermissionService.assemblePermissionModules(userPermissions);

        UserPermissionsVO userPermissionsVO = BeanKit.transform(currentUser, new UserPermissionsVO(modules));
        userPermissionsVO.setUserLevel(groupLevel.getValue());

        return userPermissionsVO;
    }

    @Override
    public UserInfoVO getUserInfo() {
        CmsUserDO currentUser = UserKit.getUser();
        List<CmsGroupDO> userGroups = this.cmsGroupService.getUserGroups(currentUser.getId());

        return BeanKit.transform(currentUser, new UserInfoVO(userGroups));
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
    public List<CmsPermissionDO> getUserPermissions(Long uid) {
        List<Long> userAllGroupIds = this.cmsUserGroupService.getUserAllGroupIds(uid);
        if (userAllGroupIds.isEmpty()) return Collections.emptyList();

        return this.cmsPermissionService.getPermissionsByGroupIds(userAllGroupIds);
    }

    @Override
    public Page<CmsUserDO> getUserPageByGroupLevelGE(Page<CmsUserDO> page, GroupLevel level) {
        return this.getBaseMapper()
                   .getUserPageByGroupLevelGE(page, level.getValue());
    }

    @Override
    public Page<CmsUserDO> getUserPageByGroupLevelEQ(Page<CmsUserDO> page, GroupLevel level) {
        return this.getBaseMapper()
                   .getUserPageByGroupLevelEQ(page, level.getValue());
    }

    @Override
    public Page<CmsUserDO> getUserAdminPageByRoot(Page<CmsUserDO> page) {
        Long rootGroupId = this.cmsGroupService.getRootGroupId();
        CmsUserDO cmsUserDO = UserKit.getUser();
        CmsUserGroupDO userGroupRelation = this.cmsUserGroupService.getUserGroupRelation(cmsUserDO.getId(),
                                                                                         rootGroupId);
        if (ObjectUtils.isEmpty(userGroupRelation)) throw new UnAuthorizedException(10222);

        return this.lambdaQuery()
                   .ne(CmsUserDO::getId, userGroupRelation.getUserId())
                   .page(page);
    }

    @Override
    public Page<CmsUserDO> getAdminPageByRoot(Page<CmsUserDO> page) {
        return this.getUserPageByGroupLevelEQ(page, GroupLevel.ADMIN);
    }

    @Override
    public Page<CmsUserDO> getUserPageByGroupId(Page<CmsUserDO> page, Long groupId) {
        return this.getBaseMapper()
                   .getUserPageByGroupId(page, groupId);
    }

    @Override
    public Tokens getRefreshToken() {
        CmsUserDO user = UserKit.getUser();
        return this.jwt.createTokens(user.getId());
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

    private void validateUserNameExist(String userName) {
        Boolean isUserNameExist = this.checkUserExistByUserName(userName);
        if (isUserNameExist) throw new ForbiddenException(10101);
    }

    private void validateEmailExist(String email) {
        Boolean isEmailExist = this.checkUserExistByEmail(email);
        if (isEmailExist) throw new ForbiddenException(10102);
    }
}
