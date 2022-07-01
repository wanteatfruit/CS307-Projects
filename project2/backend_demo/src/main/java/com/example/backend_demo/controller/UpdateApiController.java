package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.FileData;
import com.example.backend_demo.service.UpdateApiServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class UpdateApiController {

    @Resource
    UpdateApiServiceImpl service;

    @PostMapping("api/updord")
    @ResponseBody
    public void updateOrders(@RequestBody FileData file){
        String path="src/";
        String name=file.getFilename();
        path+=name;
        service.updateOrder(path);
    }

    @PostMapping("api/updUnfinish")
    @ResponseBody
    public void updateUnfinishedOrders(@RequestBody FileData file){
        String path="src/";
        String name=file.getFilename();
        path+=name;
        service.updateUnfinished(path);
    }

    @GetMapping("api/updtype")
    public void updateUnfinishedOrders(){
        service.updateType();
    }
}
