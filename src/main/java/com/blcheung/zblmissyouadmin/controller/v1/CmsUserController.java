package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.AdminRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.common.token.Tokens;
import com.blcheung.zblmissyouadmin.dto.cms.LoginDTO;
import com.blcheung.zblmissyouadmin.dto.cms.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.service.CmsUserService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import com.blcheung.zblmissyouadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/cms/user")
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
}
