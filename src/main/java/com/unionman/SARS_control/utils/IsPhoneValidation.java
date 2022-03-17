package com.unionman.SARS_control.utils;

import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.unionman.SARS_control.annotation.IsPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description:
 * @author: jinhao.xu
 * @date 2022/3/16 11:07
 */

public class IsPhoneValidation implements ConstraintValidator<IsPhone, String> {

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return false;
        } else {
            return PhoneUtil.isMobile(value);
        }
    }
}