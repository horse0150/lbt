package com.wwb.lbttrade.controller;

import com.wwb.lbttrade.model.Fund;
import com.wwb.lbttrade.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class ApiTradeController {

    @Autowired
    private FundService fundService;



    @GetMapping("/findByPhone/{phone}")
    public Fund findByPhone(@PathVariable("phone") String phone){
        return fundService.findByPhone(phone);
    }

    @GetMapping("/findByPhone2/{phone}")
    public Fund findByPhone2(@PathVariable("phone") String phone){
        return fundService.findByFeiPhone(phone);
    }
}
