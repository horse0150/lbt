package com.wwb.lbttrade.service;

import com.wwb.lbttrade.model.Fund;

public interface FundService {

    Fund findByPhone(final String phone);

    Fund findByFeiPhone(final  String phone);
}
