package com.liaohanqi.gmall.user.service;

import com.liaohanqi.gmall.bean.UmsMember;

public interface UserService {

    public UmsMember getUserById(String memberId);

    public UmsMember login(UmsMember umsMember);

    public void setUserTokenToCache(String token, String id);

    public UmsMember verifyToken(String token);

    public UmsMember getUserFromCacheById(String userId);

}
