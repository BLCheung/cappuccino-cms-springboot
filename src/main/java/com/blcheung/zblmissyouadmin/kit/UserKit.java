package com.blcheung.zblmissyouadmin.kit;

import com.blcheung.zblmissyouadmin.model.CmsUserDO;

/**
 * 用户线程池，获取线程池内请求的用户
 *
 * @author BLCheung
 * @date 2022/1/1 3:59 上午
 */
public class UserKit {

    private static final ThreadLocal<CmsUserDO> localUser = new ThreadLocal<>();

    /**
     * 获取当前请求线程的的用户
     *
     * @return com.blcheung.zblmissyouadmin.model.CmsUserDO
     * @author BLCheung
     * @date 2022/1/10 8:45 下午
     */
    public static CmsUserDO getUser() {
        return UserKit.localUser.get();
    }

    /**
     * 设置当前请求线程的用户
     *
     * @param cmsUserDO
     * @author BLCheung
     * @date 2022/1/10 8:46 下午
     */
    public static void setUser(CmsUserDO cmsUserDO) {
        UserKit.localUser.set(cmsUserDO);
    }

    /**
     * 清除当前线程的用户
     *
     * @author BLCheung
     * @date 2022/1/10 8:47 下午
     */
    public static void clearUser() {
        UserKit.localUser.remove();
    }
}
