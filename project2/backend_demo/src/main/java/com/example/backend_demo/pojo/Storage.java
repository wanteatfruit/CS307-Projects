package com.example.backend_demo.pojo;

public class Storage {
    int id;
    String supply_center;
    String product_model;
    int quantity;

    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
