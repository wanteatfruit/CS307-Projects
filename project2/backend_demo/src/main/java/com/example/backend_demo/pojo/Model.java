package com.example.backend_demo.pojo;

public class Model {
    int id;
    String product_number;
    String product_model;
    String product_name;
    int unit_price;

    int sold_sum;

    public int getSold_sum() {
        return sold_sum;
    }

    public void setSold_sum(int sold_sum) {
        this.sold_sum = sold_sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }
}
