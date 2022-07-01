package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.Center;
import com.example.backend_demo.pojo.Generic;
import com.example.backend_demo.pojo.Model;
import com.example.backend_demo.service.ModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class ModelController {
    @Resource
    ModelServiceImpl service;

    @GetMapping("api/model")
    public List<Model> findAll(){
        return service.findAll();
    }

    @PostMapping(value ="/api/model/sbyname")
    @ResponseBody
    public List<Model> findByParam(@RequestBody Generic model){
        System.out.println(model.getProduct_name());
        System.out.println(model.getProduct_model());
        List<Model> id=service.findByName(model.getProduct_name());
        List<Model> i=service.findByID(model.getId());
        List<Model> name=service.findByModel(model.getProduct_model());
        id.addAll(name);
        id.addAll(i);
        return id;
    }

    @PostMapping("/api/model/insert")
    @ResponseBody
    public void insert(@RequestBody Model model){
        service.insert(model);
    }

    @PostMapping("/api/model/delete")
    @ResponseBody
    public void delete(@RequestBody Model model){
        service.delete(model);
    }
    @PostMapping("api/model/update")
    @ResponseBody
    public void update(@RequestBody Model model){
        System.out.println(model.getUnit_price());
        service.update(model);
    }



}
