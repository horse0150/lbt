package com.wwb.lbttrade.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wwb.lbttrade.dao.FundMapper;
import com.wwb.lbttrade.model.Fund;
import com.wwb.lbttrade.model.User;
import com.wwb.lbttrade.service.FundFeignService;
import com.wwb.lbttrade.service.FundService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class FundServiceImpl implements FundService{

    @Autowired
    private FundMapper fundMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FundFeignService fundFeignService;
    /*
    ** 使用原始的方法，没有负载均衡
    @Override
    public Fund findByPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            return null;
        }
        //校验用户信息是否存在
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("lbt-user");
        if(!CollectionUtils.isEmpty(instances)){
            String uri = String.format("%s/user/findByPhone/%s",instances.get(0).getUri().toString(),phone);
            ResponseEntity<User> user = restTemplate.exchange(uri, HttpMethod.GET,null,User.class);
            if(user != null && user.getBody() != null){
                Fund fund = new Fund();
                fund.setUserId(user.getBody().getId());
                return fundMapper.selectOne(fund);
            }
        }
        return null;
    }*/

    /**
     * 使用带有负载均衡的ribbon
     * @param phone
     * 注意：不带任何配置默认hystrixCommand，在默认情况，这个注解会将所有的远程
     * 服务调用都放到同一个线程池  增加fallbackMethod则变成了后备策略，否则为断路器模式
     * 增加 threadPoolKey则变为壁舱模式，意思就是通过引入新的线程池去调用远程方法
     *
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "initDefaultFund",
        threadPoolKey = "fundPool",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "30"),
             @HystrixProperty(name = "maxQueueSize",value = "10")
        }/*,
        commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value="10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value="75"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value="7000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value="15000"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "5")
        }*/
    )
    public Fund findByPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            return null;
        }
        String uri = String.format("http://lbt-user/user/findByPhone/%s",phone);
        ResponseEntity<User> user = restTemplate.exchange(uri, HttpMethod.GET,null,User.class);
        if(user != null && user.getBody() != null){
            Fund fund = new Fund();
            fund.setUserId(user.getBody().getId());
            return fundMapper.selectOne(fund);
        }
        return null;
    }

    @Override
    public Fund findByFeiPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            return null;
        }
        User user = fundFeignService.findByPhone(phone);
        if(user != null){
            Fund fund = new Fund();
            fund.setUserId(user.getId());
            return fundMapper.selectOne(fund);
        }
        return null;
    }

    /**
     * 该方法为后备方法，并且与原始方法在同一个类中，并且必须与
     * 原始方法往前相同的方法签名
     * @param phone
     * @return
     */
    private Fund initDefaultFund(String phone){
        Fund fund = new Fund();
        fund.setId(-1L);
        fund.setUserId(fund.getId());
        fund.setMoney(new BigDecimal(0));
        return fund;
    }
}
