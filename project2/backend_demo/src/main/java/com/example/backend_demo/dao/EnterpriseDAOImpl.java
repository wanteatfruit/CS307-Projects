package com.example.backend_demo.dao;

import com.example.backend_demo.pojo.Center;
import com.example.backend_demo.pojo.Enterprise;
import com.example.backend_demo.pojo.Model;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EnterpriseDAOImpl {
    NamedParameterJdbcTemplate template;

    public EnterpriseDAOImpl(NamedParameterJdbcTemplate template){
        this.template=template;
    }

    public List<Enterprise> findAll() {
        String sql="select * from enterprise";
        return template.query(sql,new EnterpriseRowMapper());
    }

    public List<Enterprise> findByName(String enterprise_name){
        String sql="select * from enterprise where enterprise_name=:enterprise_name";
        return template.query(sql,new MapSqlParameterSource("enterprise_name",enterprise_name),new EnterpriseRowMapper());
    }

    public List<Enterprise> findByID(String i){
        int id;
        try {
            id = Integer.parseInt(i);
            //return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return new ArrayList<>();
        }

//        int i=Integer.parseInt(id);
        String sql="select * from enterprise where id=:id";
        return template.query(sql,new MapSqlParameterSource("id",id),new EnterpriseRowMapper());
    }


    public void insert(Enterprise enterprise){
        String sql="insert into enterprise values(:id,:enterprise_name,:country,:city,:supply_center,:industry)";
        Map<String,Object> map=new HashMap<>();
        map.put("id", enterprise.getId());
        map.put("enterprise_name",enterprise.getEnterprise_name());
        map.put("country",enterprise.getCountry());
        map.put("city",enterprise.getCity());
        map.put("supply_center",enterprise.getSupply_center());
        map.put("industry",enterprise.getIndustry());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    public void delete(Enterprise enterprise){
        String sql="delete from enterprise where enterprise_name=:enterprise_name";
        Map<String,Object> map=new HashMap<>();
        map.put("enterprise_name",enterprise.getEnterprise_name());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }

    public void update(Enterprise enterprise){
        String sql="update enterprise set industry=:industry where enterprise_name=:enterprise_name";
        Map<String,Object> map=new HashMap<>();
        map.put("enterprise_name",enterprise.getEnterprise_name());
        map.put("industry",enterprise.getIndustry());
        template.execute(sql,map,(PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
    }


}
