package com.liaohanqi.gmall.user.service.Impl;

import com.liaohanqi.gmall.bean.UmsMember;
import com.liaohanqi.gmall.service.UserService;
import com.liaohanqi.gmall.user.mapper.UmsMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    public UmsMember getUserById(String memberId) {

        UmsMember umsMember = umsMemberMapper.getUserById(memberId);
        return umsMember;
    }


}
