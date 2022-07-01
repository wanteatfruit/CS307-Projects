package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Center;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CenterDAOImpl{

NamedParameterJdbcTemplate template;

public CenterDAOImpl(NamedParameterJdbcTemplate template){
    this.template=template;
}

    public List<Center> findAll() {
            String sql="select * from center";
        return template.query(sql,new CenterRowMapper());
    }

    public List<Center> findByName(String name){
        String sql="select * from center where name=:name";
       return template.query(sql,new MapSqlParameterSource("name",name),new CenterRowMapper());
    }

    public List<Center> findByID(String i){
    int id;
        try {
            id = Integer.parseInt(i);
            //return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return new ArrayList<>();
        }

//        int i=Integer.parseInt(id);
        String sql="select * from center where id=:id";
        return template.query(sql,new MapSqlParameterSource("id",id),new CenterRowMapper());
    }

    public void insertCenter(Center center){
        String sql="insert into center values(:id, :name)";
        Map<String,Object> map=new HashMap<>();
        map.put("id",center.getId());
        map.put("name",center.getName());
        template.execute(sql, map, (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    public void deleteCenter(Center center){
        String sql="delete from center where name=:name";
        Map<String,Object> map=new HashMap<>();
        map.put("name",center.getName());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }
}
