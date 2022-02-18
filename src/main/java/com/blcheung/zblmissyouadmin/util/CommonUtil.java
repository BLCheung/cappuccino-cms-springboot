package com.blcheung.zblmissyouadmin.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    /**
     * 判断一个id是否被包含在指定id集合中
     *
     * @param id  指定id
     * @param ids id集合
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/25 4:25 上午
     */
    public static Boolean isContainOneId(Long id, List<Long> ids) {
        if (ids.isEmpty()) return false;

        return ids.stream()
                  .anyMatch(idInList -> idInList.equals(id));
    }

    /**
     * 判断一个id集合内是否包含有另一个id集合的个别元素
     *
     * @param ids
     * @param otherIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/25 4:39 上午
     */
    public static Boolean isContainAnyIds(List<Long> ids, List<Long> otherIds) {
        if (ids.isEmpty()) return false;

        return ids.stream()
                  .anyMatch(id -> otherIds.stream()
                                          .anyMatch(otherId -> otherId.equals(id)));
    }

    /**
     * 两个id集合内是否都包含各自相同的id元素（包括重复的id）
     *
     * @param ids
     * @param otherIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 2:56 上午
     */
    public static Boolean isIncludeEqualIds(List<Long> ids, List<Long> otherIds) {
        if (ids.isEmpty() && otherIds.isEmpty()) return true;

        if (ids.isEmpty() || otherIds.isEmpty()) return false;

        return ids.stream()
                  .allMatch(id -> otherIds.stream()
                                          .anyMatch(oId -> oId.equals(id)));
    }

    /**
     * 两个id集合是否长度一致，并且所有id各自都包含且全等
     *
     * @param ids
     * @param otherIds
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/27 3:11 上午
     */
    public static Boolean isDistinctEqualIds(List<Long> ids, List<Long> otherIds) {
        if (ids.isEmpty() && otherIds.isEmpty()) return true;

        if (ids.size() != otherIds.size()) return false;

        return ids.stream()
                  .allMatch(id -> otherIds.stream()
                                          .anyMatch(oId -> oId.equals(id)));
    }

    /**
     * 该id集合每个id值是否都是唯一不重复的
     *
     * @param ids
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/2/18 10:25 下午
     */
    public static Boolean isDistinctIds(List<Long> ids) {
        if (ids.isEmpty()) return false;

        long size = ids.size();
        long uniqueSize = ids.stream()
                             .distinct()
                             .count();

        return size == uniqueSize;
    }
}
