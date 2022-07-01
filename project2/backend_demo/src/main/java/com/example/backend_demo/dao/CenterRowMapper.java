package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Center;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenterRowMapper implements RowMapper<Center> {

    @Override
    public Center mapRow(ResultSet rs, int rowNum) throws SQLException {
        Center center=new Center();
        center.setId(Integer.parseInt(rs.getString("id")));
        center.setName(rs.getString("name"));
        return center;
    }
}
