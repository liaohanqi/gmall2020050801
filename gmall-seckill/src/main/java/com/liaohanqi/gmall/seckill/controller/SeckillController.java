package com.liaohanqi.gmall.seckill.controller;

import com.liaohanqi.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SeckillController {

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("killone")
    @ResponseBody
    public Long killone(HttpServletRequest request){

        String userId = "";

        userId = (String) request.getAttribute("userId");
        Jedis jedis = redisUtil.getJedis();
        //分布式事务，有userId返回OK，无则无返回
        String OK = jedis.set("user:" + userId + ":seckill", "1", "nx", "px", 1000 * 10);
        //监控
        jedis.watch("stock");
        Long stock = Long.parseLong(jedis.get("stock"));

        if(1 == 1){
            //开启事务
            Transaction multi = jedis.multi();
            if (stock >0){
                multi.incrBy("stock",-1);
                List<Object> exec = multi.exec();
                if (exec.size()>0){
                    System.out.println("请购成功，当前库存剩余数量" + exec.get(0));
                    jedis.set("user:" + userId + ":seckill","1","nx","px",1000*10);


                    //抢到库存后，发消息生成订单
                }else {
                    System.out.println(request.getRemoteAddr() + ":非洲人抢不到。。。");
                }
            }else {
                System.out.println("请购失败，活动结束，当前库存剩余数量" + stock);
            }
        }

        jedis.close();
        return stock;
    }

}
