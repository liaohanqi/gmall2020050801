package com.liaohanqi.gmall.manager.mapper;

import com.liaohanqi.gmall.bean.PmsBaseAttrInfo;
import com.liaohanqi.gmall.bean.PmsBaseAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsBaseAttrValueMapper extends Mapper<PmsBaseAttrValue> {
    List<PmsBaseAttrInfo> selectAttrValueListByValueIds(String join);
}
