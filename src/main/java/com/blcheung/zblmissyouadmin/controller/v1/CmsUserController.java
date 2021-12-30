package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.dto.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.service.CmsUserService;
import com.blcheung.zblmissyouadmin.vo.CreatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

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
public class CmsUserController {

    @Autowired
    private CmsUserService cmsUserService;

    @PostMapping("/register")
    public CreatedVO registerUser(@RequestBody @Validated RegisterUserDTO dto) {
        CmsUserDO cmsUserDO = this.cmsUserService.registerUser(dto);
        return ResultKit.resolveCreated(cmsUserDO);
    }
}
