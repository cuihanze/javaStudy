package com.cui.java.study.web.spring.errorhandle.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author cuihanze
 */
public class CannotHaveBlankValidator implements ConstraintValidator<CannotHaveBlank, String> {
    @Override
    public void initialize(CannotHaveBlank constraintAnnotation) {

    }

    @Override public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //null时不进行校验
        if (value != null && value.contains(" ")) {
                //获取默认提示信息
            String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
            System.out.println("default message :" + defaultConstraintMessageTemplate);
            //禁用默认提示信息
            constraintValidatorContext.disableDefaultConstraintViolation();
            //设置提示语
            constraintValidatorContext.buildConstraintViolationWithTemplate("can not contains blank").addConstraintViolation();
            return false;
        }
        return true;
    }
}
