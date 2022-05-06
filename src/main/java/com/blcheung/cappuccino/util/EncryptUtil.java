package com.blcheung.cappuccino.util;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;

/**
 * 加密工具
 *
 * @author BLCheung
 * @date 2021/12/28 12:30 上午
 */
public class EncryptUtil {

    /**
     * 加密密码
     *
     * @param password 待加密的明文密码
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/29 10:34 下午
     */
    public static String encrypt(String password) {
        char[] chars = password.toCharArray();
        return Hash.password(chars)
                   .algorithm(Type.PBKDF2_SHA256)
                   .create();
    }

    /**
     * 解密密码
     *
     * @param encryptPassword 被加密的密码
     * @param plaintPassword  明文密码
     * @return boolean
     * @author BLCheung
     * @date 2021/12/29 10:34 下午
     */
    public static boolean decrypt(String encryptPassword, String plaintPassword) {
        char[] chars = plaintPassword.toCharArray();
        try {
            return Hash.password(chars)
                       .algorithm(Type.PBKDF2_SHA256)
                       .verify(encryptPassword);
        } catch (InvalidHashException e) {
            e.printStackTrace();
            return false;
        }
    }
}
