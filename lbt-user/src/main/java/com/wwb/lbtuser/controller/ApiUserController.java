package com.wwb.lbtuser.controller;

import com.wwb.lbtuser.model.User;
import com.wwb.lbtuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ApiUserController {

    @Autowired
    private UserService userService;
    @GetMapping("/findByPhone/{phone}")
    public User findByPhone(@PathVariable("phone") String phone){
        return userService.findByPhone(phone);
    }
}
