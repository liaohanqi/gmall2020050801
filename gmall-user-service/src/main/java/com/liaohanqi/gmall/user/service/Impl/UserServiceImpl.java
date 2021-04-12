package com.liaohanqi.gmall.user.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.service.UserService;
import com.liaohanqi.gmall.user.mapper.UmsMemberMapper;
import com.liaohanqi.gmall.bean.UmsMember;
import com.liaohanqi.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

//@Service
@Service
public class UserServiceImpl implements UserService {

    @Autowired
//    @Reference
    UmsMemberMapper umsMemberMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public UmsMember getUserById(String memberId) {

        UmsMember umsMember = umsMemberMapper.getUserById(memberId);

        return umsMember;
    }

    @Override
    public UmsMember login(UmsMember umsMember) {

        UmsMember umsMember1 = new UmsMember();
        umsMember1.setNickname(umsMember.getUsername());
        umsMember1.setPassword(umsMember.getPassword());
        UmsMember umsMember2 = umsMemberMapper.selectOne(umsMember1);

        return umsMember2;
    }

    //将token存入缓存里
    @Override
    public void setUserTokenToCache(String token, String id) {

        UmsMember umsMember = new UmsMember();
        umsMember.setId(id);
        UmsMember umsMember1 = umsMemberMapper.selectOne(umsMember);

        Jedis jedis = redisUtil.getJedis();
        jedis.setex("user:"+ token + ":token",60*60*2, JSON.toJSONString(umsMember1));

        jedis.close();
    }

    //将
    @Override
    public UmsMember verifyToken(String token) {

        UmsMember umsMember = null;

        Jedis jedis = redisUtil.getJedis();
        String userJson = jedis.get("user:" + token + ":token");

        if (StringUtils.isNotBlank(userJson)){
            umsMember = JSON.parseObject(userJson, UmsMember.class);
        }

        jedis.close();

        return umsMember;
    }

    @Override
    public UmsMember getUserFromCacheById(String userId) {

        UmsMember umsMember =null;

        Jedis jedis = redisUtil.getJedis();
        String userJson = jedis.get("user:" + userId + ":info");

        if(StringUtils.isNotBlank(userJson)){
            umsMember = JSON.parseObject(userJson,UmsMember.class);
        }
        return umsMember;
    }


}
