package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.Enterprise;
import com.example.backend_demo.pojo.Generic;
import com.example.backend_demo.pojo.Model;
import com.example.backend_demo.service.EnterpriseServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class EnterpriseController {
    @Resource
    EnterpriseServiceImpl service;

    @CrossOrigin
    @GetMapping("/api/ent")
    public List<Enterprise> findAll(){return service.findAll();}

    @PostMapping(value ="/api/ent/sbyname")
    @ResponseBody
    public List<Enterprise> findByParam(@RequestBody Generic enterprise){
//        System.out.println(enterprise.getProduct_name());
//        System.out.println(enterprise.getProduct_model());
        List<Enterprise> id=service.findByName(enterprise.getEnterprise());
        List<Enterprise> i=service.findByID(enterprise.getId());
        id.addAll(i);
        return id;
    }

    @PostMapping("/api/ent/insert")
    @ResponseBody
    public void insert(@RequestBody Enterprise enterprise){
        service.insert(enterprise);
    }

    @PostMapping("/api/ent/delete")
    @ResponseBody
    public void delete(@RequestBody Enterprise model){
        service.delete(model);
    }
    @PostMapping("api/ent/update")
    @ResponseBody
    public void update(@RequestBody Enterprise model){
//        System.out.println(model.getUnit_price());
        service.update(model);
    }

}
