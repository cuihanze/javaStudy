package com.cui.java.study.spring.tx;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountDTO implements Serializable {
    private int id;
    private String name;
    private BigDecimal money;
    private Date addTime;
    private Date updateTime;
}
