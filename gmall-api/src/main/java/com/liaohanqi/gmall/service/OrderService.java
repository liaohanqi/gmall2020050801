package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.OmsOrder;

public interface OrderService {
    OmsOrder getOrderByUserId(String userId, String orderSn);

    OmsOrder getOrderByUserSn(String ordersn);
}
