package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/26 5:42 下午
 */
@Getter
@Setter
public class UserAddressVO {

    private Long id;

    private Long userId;

    private String name;

    private String mobile;

    private String address;

    private String addressDetail;

    private Integer isDefault;

}
