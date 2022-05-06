package com.blcheung.cappuccino.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.cappuccino.dto.UserPageDTO;
import com.blcheung.cappuccino.model.UserDO;
import com.blcheung.cappuccino.vo.UserDetailVO;
import com.blcheung.cappuccino.vo.UserVO;
import com.blcheung.cappuccino.vo.common.PagingVO;

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
     * @return com.blcheung.cappuccino.model.UserDO
     * @author BLCheung
     * @date 2022/2/26 5:46 下午
     */
    UserDO getUser(Long userId);

    /**
     * 获取用户分页
     *
     * @param dto
     * @return com.blcheung.cappuccino.vo.common.PagingVO<com.blcheung.cappuccino.vo.UserVO>
     * @author BLCheung
     * @date 2022/2/26 5:30 下午
     */
    PagingVO<UserVO> getUserPage(UserPageDTO dto);

    /**
     * 获取用户详情
     *
     * @param userId
     * @return com.blcheung.cappuccino.vo.UserDetailVO
     * @author BLCheung
     * @date 2022/2/26 5:45 下午
     */
    UserDetailVO getUserDetail(Long userId);
}
