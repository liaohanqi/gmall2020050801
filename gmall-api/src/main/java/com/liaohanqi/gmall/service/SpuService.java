package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.PmsBaseSaleAttr;
import com.liaohanqi.gmall.bean.PmsProductImage;
import com.liaohanqi.gmall.bean.PmsProductInfo;
import com.liaohanqi.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);
}
