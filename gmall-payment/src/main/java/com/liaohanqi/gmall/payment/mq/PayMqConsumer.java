package com.liaohanqi.gmall.payment.mq;

import com.liaohanqi.gmall.bean.Order01;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

/**
 * 减库存的消息队列消费端接口
 * 消费队列名	ORDER_RESULT_QUEUE
 * 消息数据类型	json
 * 消息传入参数	orderId	订单系统的订单ID
 * 	consignee	收货人
 * 	consigneeTel	收货电话
 * 	orderComment	订单备注
 * 	orderBody	订单概要
 * 	deliveryAddress	发货地址
 * 	paymentWay	支付方式：  ‘1’ 为货到付款，‘2’为在线支付。
 *
 * 	details:
 * skuId
 * skuNum
 * skuName	购买商品明细
 * 例如：
 * details:[{skuId:101,skuNum:1,skuName:
 * ’小米手64G’},
 * {skuId:201,skuNum:1,skuName:’索尼耳机’}]
 */
@Component
public class PayMqConsumer {

    @JmsListener(containerFactory = "jmsQueueListener",destination = "ORDER_RESULT_QUEUE")
    public void orderPayConsum01(Order01 order01){

    }





}
