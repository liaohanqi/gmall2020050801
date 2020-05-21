package com.liaohanqi.gmall.cart.service.ipml;

import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.bean.OmsCartItem;
import com.liaohanqi.gmall.cart.mapper.OmsCartItemMapper;
import com.liaohanqi.gmall.service.CartService;
import com.liaohanqi.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl implements CartService {

    @Autowired
    OmsCartItemMapper omsCartItemMapper;


    @Autowired
    RedisUtil redisUtil;

    @Override
    public OmsCartItem isCartExists(String userId, OmsCartItem omsCartItem) {

        OmsCartItem omsCartItem1 = new OmsCartItem();
        omsCartItem1.setMemberId(userId);
        omsCartItem1.setProductSkuId(omsCartItem.getProductSkuId());

        OmsCartItem omsCartItem2 = omsCartItemMapper.selectOne(omsCartItem1);
        return omsCartItem2;
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {

        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("id",omsCartItemFromDb.getId());
        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb,example);

        //同步缓存
        Jedis jedis = redisUtil.getJedis();

        jedis.hset("user:" + omsCartItemFromDb.getMemberId() + ":cart",omsCartItemFromDb.getProductSkuId(), JSON.toJSONString(omsCartItemFromDb));

        jedis.close();


    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {

        omsCartItemMapper.insertSelective(omsCartItem);
        //同步缓存
        Jedis jedis = redisUtil.getJedis();

        jedis.hset("user:"+omsCartItem.getMemberId()+":cart",omsCartItem.getProductSkuId(), JSON.toJSONString(omsCartItem));
        jedis.close();
    }

    @Override
    public List<OmsCartItem> cartList(String userId) {

        List<OmsCartItem> omsCartItems = new ArrayList<>();

        //同步缓存
        Jedis jedis = redisUtil.getJedis();
        List<String> hvals = jedis.hvals("user:" + userId + ":cart");

        if(hvals != null && hvals.size() > 0){

            //缓存有
            for (String hval : hvals) {
                OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                omsCartItems.add(omsCartItem);
            }
        }else{
            //缓存没有
            OmsCartItem omsCartItem = new OmsCartItem();
            omsCartItem.setMemberId(userId);
            omsCartItems = omsCartItemMapper.select(omsCartItem);

            if(omsCartItem != null && omsCartItems.size() > 0){

                //同步缓存
                Map<String, String> map = new HashMap<>();
                for (OmsCartItem cartItem : omsCartItems) {
                    map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));

                }
                jedis.hmset("user:" + userId + ":cart",map);
            }

        }
        jedis.close();
        return omsCartItems;
    }
}
