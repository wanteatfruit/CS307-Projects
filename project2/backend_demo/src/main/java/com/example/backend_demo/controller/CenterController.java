package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.Center;
import com.example.backend_demo.pojo.Generic;
import com.example.backend_demo.service.CenterServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
//@RequestMapping("api/main")
public class CenterController {

    @Resource
    CenterServiceImpl centerService;

    @GetMapping(value="/api/center")
    public List<Center> findCenter(){
        System.out.println("here");
        return centerService.findAll();
    }

    @PostMapping(value ="/api/center/sbyname")
    @ResponseBody
    public List<Center> findByName(@RequestBody Generic center){
        System.out.println(center);
        System.out.println(center.getName());
        List<Center> id=centerService.findByID(center.getId());
        List<Center> name=centerService.findByName(center.getName());
        id.addAll(name);
        return id;
    }

    @PostMapping("/api/center/insert")
    @ResponseBody
    public void insert(@RequestBody Center center){
        centerService.insertCenter(center);
    }

    @PostMapping("/api/center/delete")
    @ResponseBody
    public void delete(@RequestBody Center center){
        centerService.deleteCenter(center);
    }


}
