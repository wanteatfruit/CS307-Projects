package com.example.backend_demo.pojo;

public class Contract {
    String contract_number;
    String enterprise;
    String product_model;
    int quantity;
    String contract_manager;
    String contract_date;
    String est_date;
    String lod_date;
    String salesman_num;
    String contract_type;

    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getContract_manager() {
        return contract_manager;
    }

    public void setContract_manager(String contract_manager) {
        this.contract_manager = contract_manager;
    }

    public String getContract_date() {
        return contract_date;
    }

    public void setContract_date(String contract_date) {
        this.contract_date = contract_date;
    }

    public String getEst_date() {
        return est_date;
    }

    public void setEst_date(String est_date) {
        this.est_date = est_date;
    }

    public String getLod_date() {
        return lod_date;
    }

    public void setLod_date(String lod_date) {
        this.lod_date = lod_date;
    }

    public String getSalesman_num() {
        return salesman_num;
    }

    public void setSalesman_num(String salesman_num) {
        this.salesman_num = salesman_num;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }
}
