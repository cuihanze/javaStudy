package com.cui.java.study.web.spring.errorhandle.validation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller @RequestMapping("/test/") @Validated public class MethodParamValidController {
    @RequestMapping("methodParam")
    @ResponseBody
    public String foo(@Min(18) Integer age) {
        System.out.println(age);
        return "success";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map handleConstraintViolationException(ConstraintViolationException cve){
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : cves) {
            System.out.println(constraintViolation.getMessage());
        }
        Map map = new HashMap();
        map.put("errorCode",500);
        return map;
    }
}
