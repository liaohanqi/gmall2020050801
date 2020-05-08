package com.liaohanqi.gmall.user.mapper;

import com.liaohanqi.gmall.user.bean.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UmsMemberMapper {

//    UmsMember getUserById(@Param("memberId") String memberId);
      UmsMember getUserById(String memberId);

}

