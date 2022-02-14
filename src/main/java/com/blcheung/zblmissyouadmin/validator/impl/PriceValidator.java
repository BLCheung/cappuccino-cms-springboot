package com.blcheung.zblmissyouadmin.validator.impl;

import com.blcheung.zblmissyouadmin.validator.Price;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BLCheung
 * @date 2022/2/13 9:53 下午
 */
public class PriceValidator implements ConstraintValidator<Price, String> {

    private boolean allowNull;

    @Override
    public void initialize(Price constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (this.allowNull) return true;

        String reg = "(^[1-9]\\d*(.\\d{1,2})?$)|(^0(.\\d{1,2})?$)";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
