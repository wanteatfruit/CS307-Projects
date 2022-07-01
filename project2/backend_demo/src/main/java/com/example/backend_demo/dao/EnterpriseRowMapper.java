package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Enterprise;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterpriseRowMapper implements RowMapper<Enterprise> {
    @Override
    public Enterprise mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enterprise enterprise=new Enterprise();
        enterprise.setId(rs.getInt("id"));
        enterprise.setEnterprise_name(rs.getString("enterprise_name"));
        enterprise.setSupply_center(rs.getString("supply_center"));
        enterprise.setCity(rs.getString("city"));
        enterprise.setCountry(rs.getString("country"));
        enterprise.setIndustry(rs.getString("industry"));
        return enterprise;
    }
}
