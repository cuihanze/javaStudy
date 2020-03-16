package com.cui.java.study.spring.tx;

import java.math.BigDecimal;

public interface AccountService {
    boolean transferMoney(String fromName, String toName, BigDecimal money);

    boolean transferMoneyByTx(String fromName, String toName, BigDecimal money);

    boolean transferMoneyByTxByAspectJ(String fromName, String toName, BigDecimal money);
}
