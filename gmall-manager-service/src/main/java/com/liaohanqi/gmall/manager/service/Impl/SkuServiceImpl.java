package com.liaohanqi.gmall.manager.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liaohanqi.gmall.bean.*;
import com.liaohanqi.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuImageMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuInfoMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.liaohanqi.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    //1、保存sku，生成主键
    //2、保存skuimgae
    //3、保存sku销售属性
    //4、保存sku平台属性
    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //保存sku,生成主键
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String id = pmsSkuInfo.getId();

        //保存skuimgae
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(id);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        //保存sku销售属性
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(id);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        //保存sku平台属性
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(id);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

    }
}
