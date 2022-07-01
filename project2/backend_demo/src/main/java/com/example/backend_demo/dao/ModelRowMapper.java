package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Model;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelRowMapper implements RowMapper<Model> {
    @Override
    public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
        Model model=new Model();
        model.setId(Integer.parseInt(rs.getString("id")));
        model.setProduct_name(rs.getString("product_name"));
        model.setProduct_model(rs.getString("product_model"));
        model.setUnit_price(rs.getInt("unit_price"));
        model.setProduct_number(rs.getString("product_number"));
        return model;
    }
}
