package com.blcheung.cappuccino.validator;

import com.blcheung.cappuccino.validator.impl.PriceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author BLCheung
 * @date 2022/2/13 9:50 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Constraint(validatedBy = { PriceValidator.class })
public @interface Price {

    boolean allowNull() default false;

    String message() default "{validator.price}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
