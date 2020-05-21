package com.liaohanqi.gmall.order.controller;

import com.liaohanqi.gmall.annotations.LoginRequired;
import com.liaohanqi.gmall.bean.UmsMember;
import com.liaohanqi.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class orderController {

    @Autowired
    UserService userService;


    @RequestMapping("index")
    public String index(){

        return "trade";
    }

    //需要通过验证才能到这一个页面的注解
    //通过springMVC的拦截器（前提，需要将主启动类放在gmall这个目录下）
    //首先这个验证功能是每个模块都要有的，也就是所有web模块都需要的。
    // 在每个controller方法进入前都需要进行检查。可以利用在springmvc中的拦截器功能。
    @LoginRequired(isNeededSuccess = true)
    @RequestMapping("toTrade")
    public String toTrader(HttpServletRequest request, ModelMap map){

        //从浏览器拿到用户id和名字
        String userId = (String) request.getAttribute("userId");
        String nickName = (String) request.getAttribute("nickName");

        //通过浏览器的id,然后从数据库里拿用户信息
        UmsMember umsMember = userService.getUserFromCacheById(userId);

        map.put("userAddressList",umsMember.getUmsMemberReceiveAddresses());

        return "trade";
    }

}
