package com.example.backend_demo.service;

import com.example.backend_demo.dao.EnterpriseDAOImpl;
import com.example.backend_demo.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseServiceImpl {
    @Autowired
    EnterpriseDAOImpl dao;

    public List<Enterprise> findAll(){return dao.findAll();}

    public List<Enterprise> findByName(String name){return dao.findByName(name);}

    public List<Enterprise> findByID(String id){return dao.findByID(id);}

    public void insert(Enterprise enterprise){dao.insert(enterprise);}

    public void update(Enterprise enterprise){dao.update(enterprise);}

    public void delete(Enterprise enterprise){dao.delete(enterprise);}
}
