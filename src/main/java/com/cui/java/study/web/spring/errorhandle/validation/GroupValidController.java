package com.cui.java.study.web.spring.errorhandle.validation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller @RequestMapping("/test/") public class GroupValidController {

    /**
     * Validated 中添加分组，只会校验对应分组的合法性
     * @param foo
     * @param bindingResult
     * @return
     */
    @RequestMapping("/drink")
    @ResponseBody
    public String foo(@Validated({GroupFoo.Adult.class}) Foo foo, BindingResult bindingResult) {
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
