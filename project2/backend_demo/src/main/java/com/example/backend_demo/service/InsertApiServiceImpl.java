package com.example.backend_demo.service;

import com.example.backend_demo.dao.InsertApiDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertApiServiceImpl {
    @Autowired
    InsertApiDAOImpl insertApiDAO;

    public void ImportOriginal(){
        insertApiDAO.insertCenter();
        insertApiDAO.insertEnterprise();
        insertApiDAO.insertModel();
        insertApiDAO.insertStaff();
    }

    public void StockIn(String file){
        insertApiDAO.stockIn(file);
    }

    public void placeOrder(String file){
        insertApiDAO.placeOrder(file);
    }

    public void insertUnfinishedOrder(String file){insertApiDAO.insertUnfinishedOrder(file);}
}
