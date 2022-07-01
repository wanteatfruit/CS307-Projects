package com.example.backend_demo.service;

import com.example.backend_demo.dao.SelectApiIDAOImpl;
import com.example.backend_demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectApiServiceImpl {
    @Autowired
    SelectApiIDAOImpl selectApi;

    public List<Staff> getStaffCount() {
        return selectApi.getAllStaffCount();
    }

    public List<Contract> getOrderCount() {
        return selectApi.getOrderCount();
    }

    public List<Contract> getContractCount() {
        return selectApi.getContractCount();
    }

    public List<Storage> getNeverSoldCount() {
        return selectApi.getNeverSoldProductCount();
    }

    public List<Model> getFavoriteProductModel() {
        return selectApi.getFavoriteProductModel();
    }

    public List<Center> getAvgCenter() {
        return selectApi.getAvgCenter();
    }

    public List<Generic> getProductByNum(String pro_num) {
        return selectApi.getProductByNumber(pro_num);
    }

    public Center getCenterByNum(String con_num) {
        return selectApi.getCenterByContractNum(con_num).get(0);
    }

    public Enterprise getEntByNum(String con_num) {
        return selectApi.getEnterpriseByContractNum(con_num).get(0);
    }

    public Staff getManagerByNum(String con_num) {
        return selectApi.getManagerByContractNum(con_num).get(0);
    }

    public List<Generic> getOrderByNum(String con_num) {
        return selectApi.getOrderInfoByContractNum(con_num);
    }

    public List<Generic> getContractInfoByNym(String con_num) {
        List<Contract> contractList = selectApi.getContractNum(con_num);
        List<Center> centerList = selectApi.getCenterByContractNum(con_num); //get supply center
        List<Staff> staffList = selectApi.getManagerByContractNum(con_num);//get manager
        List<Enterprise> enterpriseList = selectApi.getEnterpriseByContractNum(con_num); //get enterprise
        List<Generic> orderList = selectApi.getOrderInfoByContractNum(con_num);
        Generic contract = new Generic();
        contract.setContract_num(contractList.get(0).getContract_number());
        Generic center = new Generic();
        center.setSupply_center(centerList.get(0).getName());
        Generic manager = new Generic();
        manager.setContract_manager(staffList.get(0).getStaff_name());
        Generic enter = new Generic();
        enter.setEnterprise(enterpriseList.get(0).getEnterprise_name());
        List<Generic> list = new ArrayList<>();
        list.add(contract);
        list.add(enter);
        list.add(manager);
        list.add(center);
        list.addAll(orderList);
        return list;
    }

    public List<Price> getOrderCostGroupByEnterprise() {
        return selectApi.getOrderCostGroupByEnterprise();
    }

    public List<Price> getOrderCostGroupByModel() {
        return selectApi.getOrderCostGroupByModel();
    }

    public List<Price> getStockCostGroupByCenter() {
        return selectApi.getStockCostByCenter();
    }

    public List<Price> getStockCostGroupByModel() {
        return selectApi.getStockCostByModel();
    }

    public List<Contract> queryOrder(String contract_num, String model, String enterprise) {
        return selectApi.queryOrder(contract_num, model, enterprise);
    }
//
//
//
//    }

}
