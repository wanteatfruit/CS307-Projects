package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.FileData;
import com.example.backend_demo.service.DeleteApiServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class DeleteApiController {

    @Resource
    DeleteApiServiceImpl service;

    @PostMapping("api/delord")
    @ResponseBody
    @CrossOrigin
    public void deleteOrder(@RequestBody FileData file){
        String path="src/";
        String name=file.getFilename();
        String s=path+name;
        service.deleteOrders(s);
    }
}
