package com.blcheung.zblmissyouadmin.kit;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
     * 实体向另一个实体转换
     *
     * @param f 需要转换的实体对象
     * @param t 目标转换的实体对象
     * @return T T类型的实体对象
     * @author BLCheung
     * @date 2022/1/22 1:13 上午
     */
    public static <F, T> T transform(F f, T t) {
        if (ObjectUtils.isEmpty(f) || ObjectUtils.isEmpty(t)) {
            Class<T> targetClazz;
            ParameterizedType type;
            Type superType = t.getClass()
                              .getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                type = (ParameterizedType) superType;
                Type[] types = type.getActualTypeArguments();
                if (!ObjectUtils.isEmpty(types)) {
                    targetClazz = (Class<T>) types[ 0 ];
                    try {
                        return targetClazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        BeanUtils.copyProperties(f, t);
        return t;
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
                                BeanUtils.copyProperties(f, target);
                                return target;
                            })
                            .collect(Collectors.toList());
        }
        return tList;
    }
}
