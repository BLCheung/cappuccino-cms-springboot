package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.mapper.UserAddressMapper;
import com.blcheung.cappuccino.model.UserAddressDO;
import com.blcheung.cappuccino.service.UserAddressService;
import com.blcheung.cappuccino.vo.UserAddressVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressDO>
        implements UserAddressService {

    @Override
    public List<UserAddressVO> getUserAddress(Long userId) {
        if (userId == null) return Collections.emptyList();
        List<UserAddressDO> addressList = this.lambdaQuery()
                                              .eq(UserAddressDO::getUserId, userId)
                                              .list();
        return addressList.isEmpty()
                ? Collections.emptyList()
                : BeanKit.transformList(addressList, UserAddressVO.class);
    }
}
