package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo item(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String spuId);
}
