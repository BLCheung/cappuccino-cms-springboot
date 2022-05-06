package com.blcheung.cappuccino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 规格名与规格值的实体
 * 承载规格名与规格值的数据源
 * 用于序列化为JSON数据存储进数据库
 *
 * @author BLCheung
 * @date 2022/2/15 5:26 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecKeyValueDO {

    private String key;

    private String value;

    @JsonProperty(value = "key_id")
    private Long keyId;

    @JsonProperty(value = "value_id")
    private Long valueId;

}
