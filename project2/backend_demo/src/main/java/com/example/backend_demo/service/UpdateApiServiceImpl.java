package com.example.backend_demo.service;

import com.example.backend_demo.dao.UpdateApiDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateApiServiceImpl {
    @Autowired
    UpdateApiDAOImpl updateApiDAO;

    public void updateOrder(String file){
        updateApiDAO.updateOrder(file);
    }

    public void updateUnfinished(String file){
        updateApiDAO.updateUnfinished(file);
    }
    public void updateType(){
        updateApiDAO.updateType();
    }
}
