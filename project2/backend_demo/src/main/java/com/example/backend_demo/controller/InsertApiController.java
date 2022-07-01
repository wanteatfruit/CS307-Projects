package com.example.backend_demo.controller;

import com.example.backend_demo.pojo.FileData;
import com.example.backend_demo.service.InsertApiServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class InsertApiController {
    @Resource
    InsertApiServiceImpl service;

    @GetMapping("api/import")
    public void insertOriginal(){
        service.ImportOriginal();
    }

    @PostMapping("api/stockin")
    @CrossOrigin
    @ResponseBody
    public void stockIn(@RequestBody FileData file){
        System.out.println(file);
        String path="src/";
        path+=(file.getFilename());
        System.out.println(path);
        service.StockIn(path);
    }

    @PostMapping("api/plaord")
    @CrossOrigin
    @ResponseBody
    public void placeOrder(@RequestBody FileData file){
        System.out.println(file);
        String path="src/";
        path+=(file.getFilename());
        System.out.println(path);
        service.placeOrder(path);
    }

    @PostMapping("api/insertUnfinished")
    @CrossOrigin
    @ResponseBody
    public void insertUnfinishedOrder(@RequestBody FileData file){
        System.out.println(file);
        String path="src/";
        path+=(file.getFilename());
        System.out.println(path);
        service.insertUnfinishedOrder(path);
    }
}
