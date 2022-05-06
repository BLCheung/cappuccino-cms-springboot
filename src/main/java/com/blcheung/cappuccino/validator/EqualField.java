package com.blcheung.cappuccino.validator;

import com.blcheung.cappuccino.validator.impl.EqualFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 字段相等
 *
 * @author BLCheung
 * @date 2021/12/10 11:43 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER,
                ElementType.CONSTRUCTOR })
@Constraint(validatedBy = { EqualFieldValidator.class })
public @interface EqualField {

    String field() default "";

    String confirmField() default "";

    String message() default "{validator.equal_field}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
