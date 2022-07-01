package com.example.backend_demo.service;

import com.example.backend_demo.dao.DeleteApiDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteApiServiceImpl {
    @Autowired
    DeleteApiDAOImpl deleteApiDAO;

    public void deleteOrders(String file){
        deleteApiDAO.deleteOrder(file);
    }
}
