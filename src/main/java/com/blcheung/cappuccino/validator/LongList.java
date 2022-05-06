package com.blcheung.cappuccino.validator;

import com.blcheung.cappuccino.validator.impl.LongListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 校验整型数组
 *
 * @author BLCheung
 * @date 2021/12/11 1:06 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Constraint(validatedBy = { LongListValidator.class })
public @interface LongList {

    long min() default 1;

    long max() default Long.MAX_VALUE;

    /**
     * 是否可为null或长度为0
     *
     * @return boolean
     * @author BLCheung
     * @date 2021/12/11 1:13 上午
     */
    boolean allowBlank() default false;

    String message() default "{validator.long_list}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
