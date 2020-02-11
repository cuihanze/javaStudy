package com.cui.java.study.web.spring.errorhandle.validation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Controller
@RequestMapping("/test/")
public class ManuCheckController {
    @RequestMapping("manu")
    @ResponseBody
    public String foo(Foo foo) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Foo>> set = validator.validate(foo);
        for (ConstraintViolation<Foo> constraintViolation : set) {
            System.out.println(constraintViolation.getMessage());
        }

        System.out.println(foo);
        return "success";
    }

}
