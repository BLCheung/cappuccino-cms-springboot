package com.blcheung.zblmissyouadmin.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author BLCheung
 * @date 2021/12/2 12:55 上午
 */
public class CommonUtil {

    /**
     * 获取现在的日期
     *
     * @return java.util.Date
     * @author BLCheung
     * @date 2021/12/15 2:09 上午
     */
    public static Date getNowDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 通过秒获取未来某个日期
     *
     * @param seconds
     * @return java.util.Date
     * @author BLCheung
     * @date 2021/12/15 2:10 上午
     */
    public static Date getFutureDateWithSecond(Long seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds.intValue());
        return calendar.getTime();
    }

    /**
     * 是否未超出指定时间范围
     *
     * @param now
     * @param start
     * @param end
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2021/12/15 2:10 上午
     */
    public static Boolean isInRangeDate(Date now, Date start, Date end) {
        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime > startTime && nowTime < endTime;
    }
}
