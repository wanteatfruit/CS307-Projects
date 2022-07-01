package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Model;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ModelDAOImpl {
    NamedParameterJdbcTemplate template;

    public ModelDAOImpl(NamedParameterJdbcTemplate template){
        this.template=template;
    }

    public List<Model> findAll(){
        String sql="select * from model";
        return template.query(sql,new ModelRowMapper());
    }

    public List<Model> findByModel(String product_model){
        String sql="select * from model where product_model=:product_model";
        return template.query(sql,new MapSqlParameterSource("product_model",product_model),new ModelRowMapper());
    }

    public List<Model> findByName(String product_name){
        String sql="select * from model where product_name=:product_name";
        return template.query(sql,new MapSqlParameterSource("product_name",product_name),new ModelRowMapper());
    }

    public List<Model> findByID(String i){
        int id;
        try {
            id = Integer.parseInt(i);
            //return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return new ArrayList<>();
        }
//        int i=Integer.parseInt(id);
        String sql="select * from model where id=:id";
        return template.query(sql,new MapSqlParameterSource("id",id),new ModelRowMapper());

    }

    public void insert(Model model){
        String sql="insert into model values(:id,:product_number,:product_model,:product_name,:unit_price)";
        Map<String,Object> map=new HashMap<>();
        map.put("id", model.getId());
        map.put("product_number",model.getProduct_number());
        map.put("product_model",model.getProduct_model());
        map.put("product_name",model.getProduct_name());
        map.put("unit_price",model.getUnit_price());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    public void delete(Model model){
        String sql="delete from model where product_model=:product_model";
        Map<String,Object> map=new HashMap<>();
        map.put("product_model",model.getProduct_model());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    public void update(Model model){
        String sql="update model set unit_price=:unit_price where product_model=:product_model";
        Map<String,Object> map=new HashMap<>();
        map.put("product_model",model.getProduct_model());
        map.put("unit_price",model.getUnit_price());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);

    }


}
