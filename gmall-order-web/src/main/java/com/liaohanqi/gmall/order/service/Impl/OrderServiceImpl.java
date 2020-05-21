package com.liaohanqi.gmall.order.service.Impl;

import com.liaohanqi.gmall.bean.OmsOrder;
import com.liaohanqi.gmall.bean.OmsOrderItem;
import com.liaohanqi.gmall.order.mapper.OmsOrderItemMapper;
import com.liaohanqi.gmall.order.mapper.OmsOrderMapper;
import com.liaohanqi.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {


    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;

    @Override
    public OmsOrder getOrderByUserId(String userId, String orderSn) {

        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setMemberId(userId);
        omsOrder.setOrderSn(orderSn);
        OmsOrder omsOrder1 = omsOrderMapper.selectOne(omsOrder);

        return omsOrder1;
    }

    @Override
    public OmsOrder getOrderByUserSn(String ordersn) {

        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(ordersn);
        OmsOrder omsOrder1 = omsOrderMapper.selectOne(omsOrder);

        //Item 条目
        OmsOrderItem omsOrderItem = new OmsOrderItem();
        omsOrderItem.setOrderSn(ordersn);
        List<OmsOrderItem> select = omsOrderItemMapper.select(omsOrderItem);
        omsOrder1.setOmsOrderItems(select);

        return omsOrder1;
    }


}
