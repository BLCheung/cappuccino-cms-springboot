package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/26 5:43 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDetailVO extends UserVO {

    private List<UserAddressVO> addressList;

    public UserDetailVO(List<UserAddressVO> addressList) {
        this.addressList = addressList;
    }
}
