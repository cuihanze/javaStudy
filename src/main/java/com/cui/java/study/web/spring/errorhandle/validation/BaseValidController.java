package com.cui.java.study.web.spring.errorhandle.validation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 参考 https://blog.csdn.net/qb170217/article/details/81232945
 */
@Controller
@RequestMapping("/test/")
public class BaseValidController {

    /**
     * 注：
     * 1. 输入使用 http://localhost:8080/test/baseValid?name=s&age=19&phone=18802930191&email=cuihanze@hotmail.com
     * 2. 参数Foo前需要加上@Validated注解，表明需要spring对其进行校验
     * 3. 校验的信息会存放到其后的BindingResult中，必须相邻，如果多个，
     *  使用 foo(@Validated Foo foo, BindingResult fooBindingResult ，@Validated Bar bar, BindingResult barBindingResult);
     * 4.
     * @param foo
     * @param bindingResult
     * @return
     */
    @RequestMapping("/baseValid")
    @ResponseBody
    public String foo(@Validated Foo foo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                System.out.println(fieldError);
            }
            return "fail";
        }
        System.out.println(foo);
        return "success";
    }
}