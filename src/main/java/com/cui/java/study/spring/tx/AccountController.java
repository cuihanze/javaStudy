package com.cui.java.study.spring.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class AccountController {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:config/transaction-context.xml");
        AccountService accountService = (AccountService) applicationContext.getBean("accountService");
//         boolean result = accountService.transferMoney("小明1", "小张", new BigDecimal(String.valueOf(10.11)));
//        boolean result = accountService.transferMoneyByTx("小明1", "小张", new BigDecimal(String.valueOf(10.11)));
        boolean result = accountService.transferMoneyByTxByAspectJ("小明1", "小张", new BigDecimal(String.valueOf(10.11)));
        System.out.println(result);


    }
}
