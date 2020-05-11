package com.liaohanqi.gmall.manager.mapper;

import com.liaohanqi.gmall.bean.PmsSkuInfo;
import com.liaohanqi.gmall.bean.PmsSkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuSaleAttrValueMapper extends Mapper<PmsSkuSaleAttrValue> {
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String spuId);
}
