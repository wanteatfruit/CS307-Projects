package com.example.backend_demo.controller;


import com.example.backend_demo.pojo.*;
import com.example.backend_demo.service.SelectApiServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SelectApiController {

    @Resource
    SelectApiServiceImpl service;

    @GetMapping("api/staffcnt")
    public List<Staff> getStaffCnt() {
        return service.getStaffCount();
    }

    @GetMapping("api/ordcnt")
    public List<Contract> getOrdercnt() {
        return service.getOrderCount();
    }

    @GetMapping("api/concnt")
    public List<Contract> getContractCnt() {
        return service.getContractCount();
    }

    @GetMapping("api/nvrsold")
    public List<Storage> getNvrSold() {
        return service.getNeverSoldCount();
    }

    @GetMapping("api/fav")
    public List<Model> getFav() {
        return service.getFavoriteProductModel();
    }


    @GetMapping("api/avg")
    public List<Center> getAvg() {
        return service.getAvgCenter();
    }

    @PostMapping("api/prodbynum")
    @CrossOrigin
    @ResponseBody
    public List<Generic> getProdByNum(@RequestBody Model pro_num) {
        return service.getProductByNum(pro_num.getProduct_number());
    }

//    @PostMapping("api/coninfo")
//    @CrossOrigin
//    @ResponseBody
//    public List<Generic> getOrderByNum(@RequestBody Contract con_num){
//        return service.getOrderByNum(con_num.getContract_number());
//    }

    @PostMapping("api/coninfo")
    @CrossOrigin
    @ResponseBody
    public List<Generic> getContractInfoByNum(@RequestBody Contract con_num) {
        return service.getContractInfoByNym(con_num.getContract_number());
    }

    @GetMapping("api/price1")
    @CrossOrigin
    @ResponseBody
    public List<Price> getPrice1() {
        return service.getOrderCostGroupByEnterprise();
    }

    @GetMapping("api/price2")
    @CrossOrigin
    @ResponseBody
    public List<Price> getPrice2() {
        return service.getOrderCostGroupByModel();
    }

    @GetMapping("api/price3")
    @CrossOrigin
    @ResponseBody
    public List<Price> getPrice3() {
        return service.getStockCostGroupByCenter();
    }

    @GetMapping("api/price4")
    @CrossOrigin
    @ResponseBody
    public List<Price> getPrice4() {
        return service.getStockCostGroupByModel();
    }

    @PostMapping("api/orderinfo/q")
    @CrossOrigin
    @ResponseBody
    public List<Contract> getOrder(@RequestBody Contract con_num) {
        System.out.println(con_num.getContract_number());
        System.out.println(con_num.getProduct_model());
        System.out.println(con_num.getEnterprise());
        return service.queryOrder(con_num.getContract_number(), con_num.getProduct_model(), con_num.getEnterprise());
    }
}
