package com.cui.java.study.spring.tx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("accountService")
public class AccountServiceImpl implements AccountService{
    @Resource
    private AccountDao accountDao;

    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;

    @Override
    public boolean transferMoney(String fromName, String toName, BigDecimal money) {
        /*if (accountDao.reduceMoney(fromName, money) == 1) {
            return accountDao.addMoney(toName, money) == 1;
        }
        return false;*/
        return transfer(fromName, toName, money);
    }

    @Override
    public boolean transferMoneyByTx(String fromName, String toName, BigDecimal money) {
        return transactionTemplate.execute(transactionStatus -> {
            if (accountDao.reduceMoney(fromName, money) == 1) {
                return accountDao.addMoney(toName, money) == 1;
            }
            return false;
        });
    }

    @Override
    @Transactional
    public boolean transferMoneyByTxByAspectJ(String fromName, String toName, BigDecimal money) {
        return transfer(fromName, toName, money);
    }

//    @Transactional
    public boolean transfer(String fromName, String toName, BigDecimal money) {
        if (accountDao.reduceMoney(fromName, money) == 1) {
            return accountDao.addMoney(toName, money) == 1;
        }
        return false;
    }
}

