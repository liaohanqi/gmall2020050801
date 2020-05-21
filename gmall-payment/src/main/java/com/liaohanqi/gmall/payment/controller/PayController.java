package com.liaohanqi.gmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.liaohanqi.gmall.annotations.LoginRequired;
import com.liaohanqi.gmall.bean.OmsOrder;
import com.liaohanqi.gmall.bean.PaymentInfo;
import com.liaohanqi.gmall.payment.config.AlipayConfig;
import com.liaohanqi.gmall.service.OrderService;
import com.liaohanqi.gmall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class PayController {

    @Autowired
    AlipayClient alipayClient;

    @Reference
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    //支付页面
    @RequestMapping("index")
    @LoginRequired
    //orderSn  订单号
    //HttpServletRequest  通过Http拿取单点登录存放的用户名和用户id
    public String index(HttpServletRequest request, String orderSn, ModelMap map){

        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");

        //通过用户id拿订单详情信息
        OmsOrder omsOrder = orderService.getOrderByUserId(userId,orderSn);

        //共计金额
        BigDecimal totalAmount = omsOrder.getTotalAmount();

        map.put("orderSn",orderSn);
        map.put("totalAmount",totalAmount);

        return "index";
    }

    //提交支付宝支付
    @RequestMapping("alipay/submit")
    @LoginRequired
    @ResponseBody
    //orderSn  订单号
    public String alipay(String orderSn){

        //根据订单号，查找出相应的订单
        OmsOrder omsOrder = orderService.getOrderByUserSn(orderSn);

        //根据订单号，保存支付信息业务
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderSn(orderSn);
        paymentInfo.setPaymentStatus("未支付");
        paymentInfo.setOrderId(omsOrder.getId());
        paymentInfo.setTotalAmount(omsOrder.getTotalAmount());
        paymentInfo.setSubject(omsOrder.getOmsOrderItems().get(0).getProductName());
        paymentService.sava(paymentInfo);

        //跳转第三方支付平台，支付宝支付页面
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        //支付同步跳转地址
        alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        //支付异步跳转地址
        alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        //填充业务参数
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no",orderSn);
        map.put("product_code","FAST_INSTANT_TRADE_PAY");
        map.put("total_amount",0.01);
        map.put("subject",omsOrder.getOmsOrderItems().get(0).getProductName());
        alipayTradePagePayRequest.setBizContent(JSON.toJSONString(map));

        String form="";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        paymentService.sendPayCheckQueue(paymentInfo,8L);

        System.out.println(form);

        return form;
    }

    //支付宝返回支付后的信息，并且将支付成功的信息放进ActiveMQ中，等别的模块调用消息
    @RequestMapping("alipay/callback/return")
    @LoginRequired
    @ResponseBody
    public String callbackReturn(HttpServletRequest request) {

        String out_trade_no = request.getParameter("out_trade_no");
        String trade_no = request.getParameter("trade_no");
        String total_amount = request.getParameter("total_amount");
        String sign = request.getParameter("sign");

        Map<String, String> map = new HashMap<>();
        map.put("sign",sign);
        boolean b =false;
        try {
            b = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipay_public_key, AlipayConfig.charset);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //更新支付信息业务
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderSn(out_trade_no);
        paymentInfo.setPaymentStatus("已支付");
        paymentInfo.setAlipayTradeNo(trade_no);
        paymentInfo.setCallbackContent(request.getQueryString());
        paymentInfo.setCallbackTime(new Date());
        paymentService.update(paymentInfo);

        //使用消息队列，此时作为消息的生产者，对支付成功的消息通知放在ActiveMQ中，等待别的模块调用
        paymentService.sendPaySuccessQueue(paymentInfo);

        return "redirect:/finish.html";
    }

    //微信支付
    @RequestMapping("wx/submit")
    @LoginRequired
    @ResponseBody
    public String wx(HttpServletRequest request,String orderSn,ModelMap modelMap){

        return "wx";
    }

}
