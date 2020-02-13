package com.cui.java.study.web.spring.errorhandle.controlleradvice;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * https://yq.aliyun.com/articles/647428
 */
@Controller
@RequestMapping("/test/")
public class ControllerAdviceTestController {
    @RequestMapping("/controllerAdvice")
    @ResponseBody
    public String foo(Integer age) {
        Assert.isTrue(age >= 0, "年龄不能小于零");
        return "success";
    }
}
