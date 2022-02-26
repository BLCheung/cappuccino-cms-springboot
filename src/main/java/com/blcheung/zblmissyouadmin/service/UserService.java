package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.dto.UserPageDTO;
import com.blcheung.zblmissyouadmin.model.UserDO;
import com.blcheung.zblmissyouadmin.vo.UserDetailVO;
import com.blcheung.zblmissyouadmin.vo.UserVO;
import com.blcheung.zblmissyouadmin.vo.common.PagingVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-26
 */
public interface UserService extends IService<UserDO> {

    /**
     * 获取用户
     *
     * @param userId
     * @return com.blcheung.zblmissyouadmin.model.UserDO
     * @author BLCheung
     * @date 2022/2/26 5:46 下午
     */
    UserDO getUser(Long userId);

    /**
     * 获取用户分页
     *
     * @param dto
     * @return com.blcheung.zblmissyouadmin.vo.common.PagingVO<com.blcheung.zblmissyouadmin.vo.UserVO>
     * @author BLCheung
     * @date 2022/2/26 5:30 下午
     */
    PagingVO<UserVO> getUserPage(UserPageDTO dto);

    /**
     * 获取用户详情
     *
     * @param userId
     * @return com.blcheung.zblmissyouadmin.vo.UserDetailVO
     * @author BLCheung
     * @date 2022/2/26 5:45 下午
     */
    UserDetailVO getUserDetail(Long userId);
}
