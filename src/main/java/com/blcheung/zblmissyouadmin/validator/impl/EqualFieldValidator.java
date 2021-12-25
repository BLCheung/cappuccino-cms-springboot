package com.blcheung.zblmissyouadmin.validator.impl;

import com.blcheung.zblmissyouadmin.validator.EqualField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * @author BLCheung
 * @date 2021/12/10 11:52 下午
 */
public class EqualFieldValidator implements ConstraintValidator<EqualField, Object> {
    private String field;
    private String confirmField;

    @Override
    public void initialize(EqualField constraintAnnotation) {
        this.field        = constraintAnnotation.field();
        this.confirmField = constraintAnnotation.confirmField();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Class<?> targetClass = o.getClass();

        Field field = ReflectionUtils.findField(targetClass, this.field);
        Field confirmField = ReflectionUtils.findField(targetClass, this.confirmField);

        try {
            field.setAccessible(true);
            confirmField.setAccessible(true);

            String fieldValue = (String) field.get(o);
            String confirmFieldValue = (String) confirmField.get(o);

            return StringUtils.equals(fieldValue, confirmFieldValue);
        } catch (Exception e) {
            return false;
        }
    }
}
