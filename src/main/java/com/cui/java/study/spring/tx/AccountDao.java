package com.cui.java.study.spring.tx;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Repository
public class AccountDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public int addMoney(String name, BigDecimal addMoney) {
//        int i = 1 / 0;
        String sql = "update account set money = money + ? where name = ?";
        return jdbcTemplate.update(sql, addMoney, name);
    }

    public int reduceMoney(String name, BigDecimal subMoney) {
        String sql = "update account set money = money - ? where name = ?";
        return jdbcTemplate.update(sql, subMoney, name);
    }

    public AccountDTO getByName(String name) {
        String sql = "select * from account where name = ?";
        RowMapper<AccountDTO> accountDTORowMapper = (rs, rowNum) -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(rs.getInt(1));
            accountDTO.setName(rs.getString(2));
            accountDTO.setMoney(rs.getBigDecimal(3));
            accountDTO.setAddTime(rs.getDate(4));
            accountDTO.setUpdateTime(rs.getDate(5));
            return accountDTO;
        };

        return jdbcTemplate.queryForObject(sql, accountDTORowMapper, name);
    }
}
