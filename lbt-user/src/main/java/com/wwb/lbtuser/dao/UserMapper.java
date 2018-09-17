package com.wwb.lbtuser.dao;

import com.wwb.lbtuser.model.User;
import com.wwb.lbtbase.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


public interface UserMapper extends BaseMapper<User> {
    @Select(
        {
            "select id,phone,state from imu_user where phone = #{phone}"
        }
    )
    User findByPhone(final String phone);

}
