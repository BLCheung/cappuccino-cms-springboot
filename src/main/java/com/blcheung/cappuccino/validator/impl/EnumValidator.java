package com.blcheung.cappuccino.validator.impl;

import com.blcheung.cappuccino.validator.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author BLCheung
 * @date 2022/2/3 8:23 下午
 */
public class EnumValidator implements ConstraintValidator<Enum, Object> {

    private Class<?> target;

    private boolean allowNull;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.target    = constraintAnnotation.target();
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null && this.allowNull) return true;

        if (!this.target.isEnum()) return false;

        Object[] enumValues = target.getEnumConstants();
        return Arrays.stream(enumValues)
                     .anyMatch(enumVal -> {
                         try {
                             Method getValue = this.target.getMethod("getValue");
                             Object val = getValue.invoke(enumVal);
                             return val.equals(value);
                         } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                             e.printStackTrace();
                             return false;
                         }
                     });
    }
}
