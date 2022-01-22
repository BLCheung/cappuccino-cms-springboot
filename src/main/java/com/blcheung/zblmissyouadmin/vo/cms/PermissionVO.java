package com.blcheung.zblmissyouadmin.vo.cms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/1/21 10:29 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class PermissionVO {

    private Long id;

    private String module;

    private String name;
}
