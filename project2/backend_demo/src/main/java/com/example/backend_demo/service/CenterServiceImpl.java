package com.example.backend_demo.service;

import com.example.backend_demo.dao.CenterDAOImpl;
import com.example.backend_demo.pojo.Center;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterServiceImpl{
    @Autowired
    CenterDAOImpl centerDAO;

    public List<Center> findAll() {
        return centerDAO.findAll();
    }

    public List<Center> findByName(String name){return centerDAO.findByName(name);}

    public List<Center> findByID(String id){return centerDAO.findByID(id);}

    public void insertCenter(Center center){centerDAO.insertCenter(center);}

    public void deleteCenter(Center center){centerDAO.deleteCenter(center);}
}
