package com.blcheung.cappuccino.validator.impl;

import com.blcheung.cappuccino.validator.LongList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 整型数组校验
 *
 * @author BLCheung
 * @date 2021/12/11 1:15 上午
 */
public class LongListValidator implements ConstraintValidator<LongList, List<Long>> {
    private Long    min;
    private Long    max;
    private Boolean allowBlank;

    @Override
    public void initialize(LongList constraintAnnotation) {
        this.min        = constraintAnnotation.min();
        this.max        = constraintAnnotation.max();
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    @Override
    public boolean isValid(List<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
        if (longs == null || longs.isEmpty()) return this.allowBlank;

        for (Long value : longs) {
            if (value < min || value > max) {
                return false;
            }
        }

        return true;
    }
}
