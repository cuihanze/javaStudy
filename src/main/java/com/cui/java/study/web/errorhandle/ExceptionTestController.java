package com.cui.java.study.web.errorhandle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/student/")
public class ExceptionTestController {
    @ResponseBody
    @RequestMapping("findAll")
    @ExceptionHandler
    public Response<Integer> getInt(@NotNull(message = "参数不能为空") String msg) {
        int data = msg.equals("1") ? 1 : -1;
        return Response.success(200, "返回成功", data);
    }
}
