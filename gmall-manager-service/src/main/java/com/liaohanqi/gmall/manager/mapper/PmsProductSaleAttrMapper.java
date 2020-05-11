package com.liaohanqi.gmall.manager.mapper;

import com.liaohanqi.gmall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {

    List<PmsProductSaleAttr> spuSaleAttrListBysql(@Param("spuId") String spuId, @Param("skuId") String skuId);
}
