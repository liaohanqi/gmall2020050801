package com.liaohanqi.gmall.user.mapper;

import com.liaohanqi.gmall.bean.UmsMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UmsMemberMapper {

//    UmsMember getUserById(@Param("memberId") String memberId);
      UmsMember getUserById(String memberId);

}

