package com.wwb.lbtuser.service;

import com.wwb.lbtuser.model.User;

public interface UserService {

    User findByPhone(final String phone);
}
