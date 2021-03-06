package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.common.exceptions.NotFoundException;
import com.blcheung.cappuccino.dto.UserPageDTO;
import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.kit.PagingKit;
import com.blcheung.cappuccino.mapper.UserMapper;
import com.blcheung.cappuccino.model.UserDO;
import com.blcheung.cappuccino.service.UserAddressService;
import com.blcheung.cappuccino.service.UserService;
import com.blcheung.cappuccino.vo.UserAddressVO;
import com.blcheung.cappuccino.vo.UserDetailVO;
import com.blcheung.cappuccino.vo.UserVO;
import com.blcheung.cappuccino.vo.common.PagingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserAddressService userAddressService;

    @Override
    public UserDO getUser(Long userId) {
        return this.lambdaQuery()
                   .eq(userId != null, UserDO::getId, userId)
                   .oneOpt()
                   .orElseThrow(() -> new NotFoundException(10701));
    }

    @Override
    public PagingVO<UserVO> getUserPage(UserPageDTO dto) {
        Page<UserDO> pageable = PagingKit.buildLatest(dto, UserDO.class);
        String keyword = dto.getKeyword();
        pageable = this.lambdaQuery()
                       .like(!StringUtils.isEmpty(dto.getKeyword()), UserDO::getId, dto.getKeyword())
                       .or()
                       .like(!StringUtils.isEmpty(keyword), UserDO::getNickname, keyword)
                       .page(pageable);
        return PagingKit.resolve(pageable, UserVO.class);
    }

    @Override
    public UserDetailVO getUserDetail(Long userId) {
        UserDO userDO = this.getUser(userId);

        // 用户地址信息
        List<UserAddressVO> userAddress = this.userAddressService.getUserAddress(userId);

        return BeanKit.transform(userDO, new UserDetailVO(userAddress));
    }
}
