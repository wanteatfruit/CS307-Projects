package com.example.backend_demo.pojo;


public class Center  {
    int id;
    String name;

    double avg;

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getAvg() {
        return avg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
