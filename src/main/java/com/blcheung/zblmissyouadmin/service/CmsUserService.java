package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsUserService extends IService<CmsUserDO> {
    /**
     * 注册用户
     *
     * @param registerUserDTO
     * @return com.blcheung.zblmissyouadmin.model.CmsUserDO
     * @author BLCheung
     * @date 2021/12/13 9:03 下午
     */
    CmsUserDO registerUser(RegisterUserDTO registerUserDTO);

    /**
     * 通过用户名检查用户是否存在
     *
     * @param username
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/13 9:03 下午
     */
    Boolean checkUserExistByUserName(String username);

    /**
     * 通过邮箱检查用户是否存在
     *
     * @param email
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/17 10:54 下午
     */
    Boolean checkUserExistByEmail(String email);
}
