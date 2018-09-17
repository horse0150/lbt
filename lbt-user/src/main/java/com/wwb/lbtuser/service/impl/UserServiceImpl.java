package com.wwb.lbtuser.service.impl;

import com.alibaba.fastjson.JSON;
import com.wwb.lbtuser.Util.Constant;
import com.wwb.lbtuser.dao.UserMapper;
import com.wwb.lbtuser.model.User;
import com.wwb.lbtuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public User findByPhone(String phone) {
        User user = null;
        Object obj = redisTemplate.opsForHash().get(Constant.USER_PH,phone);
        if(obj == null){
            user = userMapper.findByPhone(phone);
            if(user != null){
                redisTemplate.opsForHash().put(Constant.USER_PH,phone, JSON.toJSONString(user));
            }
        }else{
            user = JSON.parseObject(obj.toString(),User.class);
        }
        return user;
    }
}
