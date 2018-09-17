package com.wwb.lbttrade.service;

import com.wwb.lbttrade.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("lbt-user")
public interface FundFeignService {

    @GetMapping(
            value = "/user/findByPhone/{phone}",
            consumes = "application/json"
    )
    User findByPhone(@PathVariable("phone") final String phone);
}
