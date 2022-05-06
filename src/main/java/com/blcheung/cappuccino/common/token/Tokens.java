package com.blcheung.cappuccino.common.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 令牌实体数据
 *
 * @author BLCheung
 * @date 2021/12/14 11:24 下午
 */
@Getter
@Setter
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;
}
