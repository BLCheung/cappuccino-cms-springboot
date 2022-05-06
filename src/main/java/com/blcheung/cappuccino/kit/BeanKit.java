package com.blcheung.cappuccino.kit;

import com.blcheung.cappuccino.common.exceptions.ServerErrorException;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实体辅助类
 *
 * @author BLCheung
 * @date 2022/1/22 1:09 上午
 */
public class BeanKit {

    /**
     * 来源实体向另一个目标实体转换
     * 如果来源实体内字段与目标实体内字段的类型不一致则会导致字段拷贝失败
     *
     * @param f 需要转换的实体对象
     * @param t 目标转换的实体对象
     * @return T T类型的实体对象
     * @author BLCheung
     * @date 2022/1/22 1:13 上午
     */
    public static <F, T> T transform(F f, T t) {
        if (ObjectUtils.isEmpty(t)) throw new ServerErrorException(9999, "未知类型的数据转换出错");
        if (ObjectUtils.isEmpty(f)) {
            Class<T> targetClazz = (Class<T>) t.getClass();
            try {
                return targetClazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new ServerErrorException(9999, "无法实例化未知类型的数据");
            }
        }
        BeanUtils.copyProperties(f, t);
        return t;
    }

    /**
     * 来源实体向另一个目标实体转换
     * 如果来源实体内字段与目标实体内字段的类型不一致则会导致字段拷贝失败
     *
     * @param f
     * @param clazz
     * @return T
     * @author BLCheung
     * @date 2022/2/18 1:14 上午
     */
    public static <F, T> T transform(F f, Class<T> clazz) {
        if (ObjectUtils.isEmpty(clazz)) throw new ServerErrorException(9999, "未知类型的数据转换出错");
        if (ObjectUtils.isEmpty(f)) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new ServerErrorException(9999, "无法实例化未知类型的数据");
            }
        }
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper.map(f, clazz);
    }

    /**
     * 将数据源集合转换成一个目标实体类集合
     *
     * @param sourceList 数据源集合
     * @param tClass     目标实体类
     * @return java.util.List<T> 目标实体类的一个集合
     * @author BLCheung
     * @date 2022/1/21 9:17 下午
     */
    public static <S, T> List<T> transformList(List<S> sourceList, Class<T> tClass) {
        List<T> tList;
        if (sourceList.isEmpty()) {
            tList = Collections.emptyList();
        } else {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            tList = sourceList.stream()
                              .map(source -> mapper.map(source, tClass))
                              .collect(Collectors.toList());
        }
        return tList;
    }

    /**
     * 将一个数据源集合转换成Target目标实体集合
     * 如果数据源实体内字段与目标实体内字段的类型不一致则会拷贝失败
     *
     * @param fromList 数据源集合
     * @param target   目标实体
     * @return java.util.List<T>
     * @author BLCheung
     * @date 2022/1/22 1:00 上午
     */
    public static <F, T> List<T> transformList(List<F> fromList, T target) {
        List<T> tList;
        if (fromList.isEmpty() || ObjectUtils.isEmpty(target)) {
            tList = Collections.emptyList();
        } else {
            tList = fromList.stream()
                            .map(f -> {
                                T temp = transform(null, target);
                                BeanUtils.copyProperties(f, temp);
                                return temp;
                            })
                            .collect(Collectors.toList());
        }
        return tList;
    }
}
