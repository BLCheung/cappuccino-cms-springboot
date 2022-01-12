package com.blcheung.zblmissyouadmin.common.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.blcheung.zblmissyouadmin.common.enumeration.TokenType;
import com.blcheung.zblmissyouadmin.util.CommonUtil;

import java.util.Date;
import java.util.Map;

/**
 * 双令牌
 *
 * @author BLCheung
 * @date 2021/12/14 11:55 下午
 */
public class DoubleJWT {
    // 秘钥
    private final String secret;

    private final Long accessTokenExpired;

    private final Long refreshTokenExpired;

    // accessToken的检查器
    private JWTVerifier accessVerifier;

    // refreshToken的检查器
    private JWTVerifier refreshVerifier;

    // token生成器
    private JWTCreator.Builder builder;

    private Algorithm algorithm;

    public static final String KEY_TOKEN_IDENTITY = "identity";
    public static final String KEY_TOKEN_TYPE     = "type";

    public DoubleJWT(String secret, Long accessTokenExpired, Long refreshTokenExpired) {
        this.secret              = secret;
        this.accessTokenExpired  = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
        this.init();
    }

    /**
     * 生成双令牌
     *
     * @param identity token标识
     * @return com.blcheung.zblmissyouadmin.common.token.Tokens
     * @author BLCheung
     * @date 2021/12/30 11:16 下午
     */
    public Tokens createTokens(Long identity) {
        String accessToken = this.createAccessToken(identity);
        String refreshToken = this.createRefreshToken(identity);
        return new Tokens(accessToken, refreshToken);
    }

    /**
     * 生成accessToken
     *
     * @param identity
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/15 2:28 上午
     */
    public String createAccessToken(Long identity) {
        return this.createToken(identity, TokenType.ACCESS, this.accessTokenExpired);
    }

    /**
     * 生成refreshToken
     *
     * @param identity
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/15 2:28 上午
     */
    public String createRefreshToken(Long identity) {
        return this.createToken(identity, TokenType.REFRESH, this.refreshTokenExpired);
    }

    /**
     * 解析accessToken
     *
     * @param accessToken
     * @return java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim>
     * @author BLCheung
     * @date 2021/12/15 2:28 上午
     */
    public Map<String, Claim> decodeAccessToken(String accessToken) {
        DecodedJWT accessVerify = this.accessVerifier.verify(accessToken);

        this.checkTokenType(accessVerify.getClaim(this.KEY_TOKEN_TYPE)
                                        .asInt(), TokenType.ACCESS);
        this.checkTokenExpired(accessVerify.getExpiresAt());

        return accessVerify.getClaims();
    }

    /**
     * 解析refreshToken
     *
     * @param refreshToken
     * @return java.util.Map<java.lang.String, com.auth0.jwt.interfaces.Claim>
     * @author BLCheung
     * @date 2021/12/15 2:28 上午
     */
    public Map<String, Claim> decodeRefreshToken(String refreshToken) {
        DecodedJWT refreshVerify = this.refreshVerifier.verify(refreshToken);

        this.checkTokenType(refreshVerify.getClaim(KEY_TOKEN_TYPE)
                                         .asInt(), TokenType.REFRESH);
        this.checkTokenExpired(refreshVerify.getExpiresAt());

        return refreshVerify.getClaims();
    }


    private void init() {
        this.algorithm       = Algorithm.HMAC256(this.secret);
        this.accessVerifier  = JWT.require(this.algorithm)
                                  .acceptExpiresAt(this.accessTokenExpired)
                                  .build();
        this.refreshVerifier = JWT.require(this.algorithm)
                                  .acceptExpiresAt(this.refreshTokenExpired)
                                  .build();
        this.builder         = JWT.create();
    }

    private String createToken(Long identity, TokenType tokenType, Long tokenExpired) {
        return builder.withClaim(KEY_TOKEN_IDENTITY, identity)
                      .withClaim(KEY_TOKEN_TYPE, tokenType.getValue())
                      .withExpiresAt(CommonUtil.getFutureDateWithSecond(tokenExpired))
                      .sign(this.algorithm);
    }

    private void checkTokenType(Integer value, TokenType tokenType) {
        if (!tokenType.getValue()
                      .equals(value)) {
            throw new InvalidClaimException("当前token类型不正确");
        }
    }

    private void checkTokenExpired(Date tokenExpired) {
        Date now = new Date();
        if (now.getTime() > tokenExpired.getTime()) {
            throw new TokenExpiredException("token已过期");
        }
    }
}
