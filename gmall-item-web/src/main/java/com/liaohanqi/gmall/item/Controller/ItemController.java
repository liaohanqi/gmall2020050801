package com.liaohanqi.gmall.item.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.bean.PmsProductSaleAttr;
import com.liaohanqi.gmall.bean.PmsSkuInfo;
import com.liaohanqi.gmall.bean.PmsSkuSaleAttrValue;
import com.liaohanqi.gmall.service.SkuService;
import com.liaohanqi.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SpuService spuService;

    @Reference
    SkuService skuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){

        //查询商品详情,返回详情值
        PmsSkuInfo pmsSkuInfo = skuService.item(skuId);
        String spuId = pmsSkuInfo.getSpuId();

        //通过skuId和spuId共同去查找生产销售属性
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrListBysql(spuId,skuId);

        //根据以上查找出来的信息，放到页面上
        map.put("skuInfo",pmsSkuInfo);
        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        //在加载item页面时，从服务器下载一个json文件
        List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(spuId);

        Map<String, String> skuMap = new HashMap<>();

        for (PmsSkuInfo skuInfo : pmsSkuInfos) {
            String v = skuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            String k ="";
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                k = k + "|" + pmsSkuSaleAttrValue.getSaleAttrValueId();
            }
            skuMap.put(k,v);
        }
        String skuJson = JSON.toJSONString(skuMap);

        map.put("skuJson",skuJson);

        return "item";
    }

    @RequestMapping("test")
    public String test(ModelMap map){

        String hello = "hello thymeleaf";

        List<Object> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add("循环元素" + i);
        }

        map.put("hello",hello);
        map.put("list",list);
        map.put("user",null);
        map.put("v","点我");

        return "a";
    }

}
