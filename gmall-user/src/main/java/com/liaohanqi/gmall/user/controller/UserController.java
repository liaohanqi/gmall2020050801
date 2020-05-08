package com.liaohanqi.gmall.user.controller;

import com.liaohanqi.gmall.bean.UmsMember;
import com.liaohanqi.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){

        return "index";
    }


    @RequestMapping("getUserById")
    @ResponseBody
    public UmsMember getUserById(String memberId){

        UmsMember umsMember = userService.getUserById(memberId);

        return umsMember;
    }


}
