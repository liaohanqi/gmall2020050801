package com.liaohanqi.gmall.interceptors;

import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.annotations.LoginRequired;
import com.liaohanqi.gmall.util.CookieUtil;
import com.liaohanqi.gmall.util.HttpclientUtil;
import com.liaohanqi.gmall.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            // 判断是否需要拦截
            HandlerMethod hm = (HandlerMethod)handler;
            LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);

            if(methodAnnotation==null){
                return true;
            }

            // 用户之前登陆过，获取请求中cookie里的token

            String token = "";

            String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
            if(StringUtils.isNotBlank(oldToken)){
                token = oldToken;
            }

            // 用户第一次登录，获取请求地址栏的token
            String newToken = request.getParameter("newToken");
            if(StringUtils.isNotBlank(newToken)){
                token = newToken;
            }

            if(StringUtils.isNotBlank(token)){
                // 登陆过
                // 通过调用认证中心，验证token
                // 远程调用ws接口，rpc，http协议的远程rest风格接口，可以使用http工具
                String requestIp  = "";
                requestIp = request.getHeader("x-forward-for");
                if(StringUtils.isBlank(requestIp)){
                    requestIp = request.getRemoteAddr();
                }
                String result = HttpclientUtil.doGet("http://passport.gmall.com:8085/verify?token=" + token+"&requestIp="+requestIp);
                Map<String,String> resultMap = new HashMap<>();
                resultMap = JSON.parseObject(result, resultMap.getClass());

                /***
                 * 去中心化操作，jwt
                 */
                String ip  = "";
                ip = request.getHeader("x-forward-for");
                if(StringUtils.isBlank(ip)){
                    ip = request.getRemoteAddr();
                }
                Map<String, Object> gmall0615 = JwtUtil.decode(token, "gmall0615", ip);
                /***
                 * 去中心化操作，jwt
                 */

                if(StringUtils.isNotBlank(result)&&"success".equals(resultMap.get("success"))){
                    // 验证通过
                    // 刷新过期时间(缓存中token的过期时间，cookie中的token的过期时间)
                    // 覆盖cookie
                    CookieUtil.setCookie(request,response,"oldToken",token,30*60,true);
                    // 将用户账户基本信息写入请求
                    String userId = resultMap.get("userId");
                    String nickName = resultMap.get("nickName");
                    request.setAttribute("userId",userId);
                    request.setAttribute("nickName",nickName);
                    return true;
                }else{
                    //验证失败
                    //重新进入认证中心，登录
                    if(methodAnnotation.isNeededSuccess()==false){
                        return true;
                    }
                    response.sendRedirect("http://passport.gmall.com:8085/index?ReturnUrl="+request.getRequestURL());
                    return false;
                }
            }else{
                // 未登陆过
                if(methodAnnotation.isNeededSuccess()==false){
                    return true;
                }
                response.sendRedirect("http://passport.gmall.com:8085/index?ReturnUrl="+request.getRequestURL());
                return false;
            }

        }
}