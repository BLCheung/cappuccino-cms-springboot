package com.blcheung.cappuccino.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/15 10:59 下午
 */
@Getter
@Setter
public class SpecKeyValueVO {

    @JsonAlias(value = { "key_id" })    // 可指定名为key_id或更多其他名称的字段都可以被正确映射到keyId属性上
    private Long keyId;

    @JsonAlias(value = { "value_id" })
    private Long valueId;

    private String key;

    private String value;
}
