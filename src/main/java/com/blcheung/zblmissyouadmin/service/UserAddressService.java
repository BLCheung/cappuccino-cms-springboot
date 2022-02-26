package com.blcheung.zblmissyouadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blcheung.zblmissyouadmin.model.UserAddressDO;
import com.blcheung.zblmissyouadmin.vo.UserAddressVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-26
 */
public interface UserAddressService extends IService<UserAddressDO> {

    /**
     * 获取用户地址
     *
     * @param userId
     * @return java.util.List<com.blcheung.zblmissyouadmin.vo.UserAddressVO>
     * @author BLCheung
     * @date 2022/2/26 5:52 下午
     */
    List<UserAddressVO> getUserAddress(Long userId);
}
