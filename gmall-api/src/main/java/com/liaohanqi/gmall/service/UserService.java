package com.liaohanqi.gmall.service;


import com.liaohanqi.gmall.bean.UmsMember;

public interface UserService {

    UmsMember getUserById(String memberId);

    UmsMember login(UmsMember umsMember);

    void setUserTokenToCache(String token, String id);

    UmsMember verifyToken(String token);

    UmsMember getUserFromCacheById(String userId);
}
