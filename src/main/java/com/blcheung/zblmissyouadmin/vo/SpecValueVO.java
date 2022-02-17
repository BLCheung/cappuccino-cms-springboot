package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/17 12:49 上午
 */
@Getter
@Setter
public class SpecValueVO {

    private Long id;

    /**
     * 规格值
     */
    private String value;

    /**
     * 所属规格名的id
     */
    private Long specId;

    private String extend;
}
