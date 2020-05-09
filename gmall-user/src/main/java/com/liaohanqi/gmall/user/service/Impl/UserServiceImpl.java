package com.liaohanqi.gmall.user.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.liaohanqi.gmall.bean.UmsMember;
import com.liaohanqi.gmall.service.UserService;
import com.liaohanqi.gmall.user.mapper.UmsMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
@Service
public class UserServiceImpl implements UserService {

//    @Autowired
    @Reference
    UmsMemberMapper umsMemberMapper;

    @Override
    public UmsMember getUserById(String memberId) {

        UmsMember umsMember = umsMemberMapper.getUserById(memberId);
        return umsMember;
    }


}
