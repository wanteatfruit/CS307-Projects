package com.example.backend_demo.pojo;

public class Store_record {
    int id;
    String supply_center;
    String product_model;
    String supply_staff;
    String storage_date;
    int purchase_price;
    int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getSupply_staff() {
        return supply_staff;
    }

    public void setSupply_staff(String supply_staff) {
        this.supply_staff = supply_staff;
    }

    public String getStorage_date() {
        return storage_date;
    }

    public void setStorage_date(String storage_date) {
        this.storage_date = storage_date;
    }

    public int getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(int purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
