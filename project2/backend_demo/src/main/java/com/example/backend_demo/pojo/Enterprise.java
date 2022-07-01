package com.example.backend_demo.pojo;

public class Enterprise {
    int id;
    String enterprise_name;
    String country;
    String city;
    String supply_center;
    String industry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
