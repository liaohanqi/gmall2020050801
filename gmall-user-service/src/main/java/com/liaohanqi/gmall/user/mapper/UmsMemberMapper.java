package com.liaohanqi.gmall.user.mapper;

import com.liaohanqi.gmall.bean.UmsMember;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

//@Mapper
public interface UmsMemberMapper extends Mapper<UmsMember> {

    UmsMember getUserById(@Param("memberId") String memberId);


}

