package com.example.backend_demo.service;

import com.example.backend_demo.dao.ModelDAOImpl;
import com.example.backend_demo.pojo.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl {
    @Autowired
    ModelDAOImpl modelDAO;

    public List<Model> findAll(){return modelDAO.findAll();}

    public List<Model> findByModel(String model){return modelDAO.findByModel(model);}

    public List<Model> findByName(String name){return modelDAO.findByName(name);}

    public List<Model> findByID(String id){return modelDAO.findByID(id);}

    public void insert(Model model){modelDAO.insert(model);}

    public void delete(Model model){modelDAO.delete(model);}

    public void update(Model model){modelDAO.update(model);}
}
