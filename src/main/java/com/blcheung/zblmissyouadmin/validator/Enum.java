package com.blcheung.zblmissyouadmin.validator;

import com.blcheung.zblmissyouadmin.validator.impl.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举值校验
 *
 * @author BLCheung
 * @date 2022/2/3 8:21 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = { EnumValidator.class })
public @interface Enum {

    /**
     * 目标枚举类
     */
    Class<?> target();

    boolean allowNull() default true;

    String message() default "{validator.enum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
