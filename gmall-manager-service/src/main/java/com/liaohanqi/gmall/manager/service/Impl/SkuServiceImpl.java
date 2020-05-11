package com.liaohanqi.gmall.manager.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.bean.*;
import com.liaohanqi.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuImageMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuInfoMapper;
import com.liaohanqi.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.liaohanqi.gmall.service.SkuService;
import com.liaohanqi.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

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

    @Autowired
    RedisUtil redisUtil;

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

    //通过销售属性id(skuId)去查找数据
    //先通过缓存查找
    //缓存没有，再去数据库查找，并转化为缓存
    @Override
    public PmsSkuInfo item(String skuId) {

        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();

        //请求缓存
        Jedis jedis = redisUtil.getJedis();

        try {
            //缓存规则，怎么定义缓存key和value
            String skuInfoJson = jedis.get("skuInfo:" + skuId + ":info");//skuInfo:skuId:info

            //如果查询缓存返回是空的话，那就查询数据库
            if (StringUtils.isBlank(skuInfoJson)){
                //查询db
                pmsSkuInfo = itemFromDb(skuId);

                //查询有结果则更新到redis中
                if (pmsSkuInfo != null) {
                    jedis.set("skuInfo:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));

                    }
                //如果缓存有东西
                }else {
                    //转化为缓存
                    pmsSkuInfo = JSON.parseObject(skuInfoJson, PmsSkuInfo.class);
                }
        } finally {
            jedis.close();

        }

        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
        return pmsSkuInfos;
    }

    private PmsSkuInfo itemFromDb(String skuId) {

        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        //查找销售属性值(sku)
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);

        //查找销售属性（sku）的图片
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo1.setSkuImageList(pmsSkuImages);

        return pmsSkuInfo1;
    }


}
