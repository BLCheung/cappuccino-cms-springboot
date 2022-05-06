package com.blcheung.cappuccino.controller.v1.cms;


import com.blcheung.cappuccino.common.annotations.permission.AdminRequired;
import com.blcheung.cappuccino.common.annotations.permission.LoginRequired;
import com.blcheung.cappuccino.common.annotations.permission.RefreshRequired;
import com.blcheung.cappuccino.common.annotations.router.RouterMeta;
import com.blcheung.cappuccino.common.annotations.router.RouterModule;
import com.blcheung.cappuccino.common.exceptions.FailedException;
import com.blcheung.cappuccino.common.token.Tokens;
import com.blcheung.cappuccino.dto.cms.LoginDTO;
import com.blcheung.cappuccino.dto.cms.RegisterUserDTO;
import com.blcheung.cappuccino.dto.cms.UpdateUserInfoDTO;
import com.blcheung.cappuccino.dto.cms.UpdateUserPasswordDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.ResultKit;
import com.blcheung.cappuccino.model.CmsUserDO;
import com.blcheung.cappuccino.service.CmsUserService;
import com.blcheung.cappuccino.vo.cms.UserInfoVO;
import com.blcheung.cappuccino.vo.cms.UserPermissionsVO;
import com.blcheung.cappuccino.vo.cms.UserVO;
import com.blcheung.cappuccino.vo.common.CreatedVO;
import com.blcheung.cappuccino.vo.common.ResultVO;
import com.blcheung.cappuccino.vo.common.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/user")
@Validated  // 不加的话@PathVariable方式的参数校验可能不生效
@RouterModule(name = "用户")
public class CmsUserController {

    @Autowired
    private CmsUserService cmsUserService;

    @PostMapping("/register")
    @AdminRequired
    @RouterMeta(name = "注册用户", mount = false)
    public CreatedVO register(@RequestBody @Validated RegisterUserDTO dto) {
        CmsUserDO cmsUserDO = this.cmsUserService.registerUser(dto);
        return ResultKit.resolveCreated(cmsUserDO);
    }

    @PostMapping("/login")
    public ResultVO<Tokens> login(@RequestBody @Validated LoginDTO dto) {
        Tokens tokens = this.cmsUserService.login(dto);
        return ResultKit.resolve(tokens);
    }

    @PutMapping
    @LoginRequired
    @RouterMeta(name = "更新用户信息", mount = false)
    public UpdatedVO update(@RequestBody @Validated UpdateUserInfoDTO dto) {
        CmsUserDO cmsUserDO = this.cmsUserService.update(dto)
                                                 .orElseThrow(() -> new FailedException(10103));
        return ResultKit.resolveUpdated(BeanKit.transform(cmsUserDO, new UserVO()));
    }

    @PutMapping("/password")
    @LoginRequired
    @RouterMeta(name = "修改密码", mount = false)
    public UpdatedVO changePassword(@RequestBody @Validated UpdateUserPasswordDTO dto) {
        Boolean updateSuccess = this.cmsUserService.changePassword(dto);
        if (!updateSuccess) throw new FailedException(10120);
        return ResultKit.resolveUpdated();
    }

    @GetMapping("/permissions")
    @LoginRequired
    @RouterMeta(name = "获取用户权限", mount = false)
    public ResultVO<UserPermissionsVO> permissions() {
        UserPermissionsVO userPermissionsVO = this.cmsUserService.getUserPermissions();
        return ResultKit.resolve(userPermissionsVO);
    }

    @GetMapping("/info")
    @LoginRequired
    @RouterMeta(name = "获取用户信息", mount = false)
    public ResultVO<UserInfoVO> info() {
        UserInfoVO userInfoVO = this.cmsUserService.getUserInfo();
        return ResultKit.resolve(userInfoVO);
    }

    @GetMapping("/refresh")
    @RefreshRequired
    @RouterMeta(name = "获取刷新令牌", mount = false)
    public ResultVO<Tokens> refreshToken() {
        Tokens tokens = this.cmsUserService.getRefreshToken();
        return ResultKit.resolve(tokens);
    }
}
