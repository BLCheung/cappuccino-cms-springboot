package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.mapper.UserAddressMapper;
import com.blcheung.zblmissyouadmin.model.UserAddressDO;
import com.blcheung.zblmissyouadmin.service.UserAddressService;
import com.blcheung.zblmissyouadmin.vo.UserAddressVO;
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
