package com.liaohanqi.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.util.JwtUtil;
import com.liaohanqi.gmall.service.UserService;
import com.liaohanqi.gmall.bean.UmsMember;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Reference
    UserService userService;

    @RequestMapping("index")
    public String index(){

        return "index";
    }

    //根据传入的登录名和密码，到数据库查找匹配
    //如果有，则根据ip,用户信息，服务器密钥，最终生成token
    //如果没有，则返回token=“fail"
    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request, String loginName, String passwd, ModelMap hashMap){
        String token ="";

        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(loginName);
        umsMember.setPassword(passwd);

        //调用userService，通过数据库核对用户密码
        umsMember = userService.login(umsMember);
        //如果返回值不为空，则说明核对成功
        if (umsMember !=null){
            String serverKey = "gmall0508";
            //ip从浏览器的header拿
            String ip = request.getHeader("x-forward-for");
            //如果没拿到，则从remoteaddr拿
            if (StringUtils.isBlank(ip)){
                ip = request.getRemoteAddr();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("userId",umsMember.getId());
            map.put("nickName",umsMember.getNickname());

            //根据jwt的util(工具类)，凑合着ip,用户信息，服务器密钥，最终生成token
            token = JwtUtil.encode(serverKey, map, ip);
            //将token写入缓存
            userService.setUserTokenToCache(token,umsMember.getId());

         //如果通过数据库核对用户密码，返回值是空
        }else {

            token = "fail";
        }

        return token;
    }


    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token,HttpServletRequest request,String requestIp){

        String ip = "";
        ip = request.getHeader("x-forward-for");
        if (StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();
        }
        Map<String, Object> gmall0615 = JwtUtil.decode(token, "gmall0615", requestIp);

        Map<String, String> resultMap = new HashMap<>();
        //通过redis验证token是否正确,并返回值
        UmsMember umsMember = userService.verifyToken(token);
        //如果不为空
        if (umsMember != null){

            //获取用户信息
            resultMap.put("userId",umsMember.getId());
            resultMap.put("nickName",umsMember.getNickname());
            resultMap.put("success","success");
        }else {
            resultMap.put("success","fail");
        }

        return JSON.toJSONString(resultMap);
    }

}
