package com.blcheung.zblmissyouadmin.controller.v1;


import com.blcheung.zblmissyouadmin.common.annotations.permission.GroupRequired;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.dto.UserPageDTO;
import com.blcheung.zblmissyouadmin.kit.ResultKit;
import com.blcheung.zblmissyouadmin.service.UserService;
import com.blcheung.zblmissyouadmin.vo.UserDetailVO;
import com.blcheung.zblmissyouadmin.vo.UserVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;
import com.blcheung.zblmissyouadmin.vo.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/user")
@Validated
@GroupRequired
@RouterModule(name = "C端用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @RouterMeta(name = "查询所有C端用户", mount = false)
    public ResultVO<PagingVO<UserVO>> page(@Validated UserPageDTO dto) {
        PagingVO<UserVO> userPage = this.userService.getUserPage(dto);
        return ResultKit.resolve(userPage);
    }

    @GetMapping("/{id}")
    @RouterMeta(name = "查看用户详情", mount = false)
    public ResultVO<UserDetailVO> get(@PathVariable @Positive Long id) {
        UserDetailVO userDetailVO = this.userService.getUserDetail(id);
        return ResultKit.resolve(userDetailVO);
    }
}
