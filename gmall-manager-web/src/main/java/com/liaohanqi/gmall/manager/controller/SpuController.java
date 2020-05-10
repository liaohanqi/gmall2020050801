package com.liaohanqi.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.PmsBaseSaleAttr;
import com.liaohanqi.gmall.bean.PmsProductImage;
import com.liaohanqi.gmall.bean.PmsProductInfo;
import com.liaohanqi.gmall.bean.PmsProductSaleAttr;
import com.liaohanqi.gmall.manager.util.MyUpoladUtil;
import com.liaohanqi.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {

    @Reference
    SpuService spuService;


    //根据三级id展示出产品的商品信息（spu）
    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){

        List<PmsProductInfo> pmsProductInfo = spuService.spuList(catalog3Id);

        return pmsProductInfo;
    }

    //展示出“添加spu”的字段（不由前端控制，是因为不同的商品可能呈现出不一样的字段）
    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){

        List<PmsBaseSaleAttr> pmsBaseSaleAttrList = spuService.baseSaleAttrList();
        return pmsBaseSaleAttrList;
    }

    //利用fastDFS对图片进行上存和下载
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile){

        String imageUrl = MyUpoladUtil.upload_image(multipartFile);
        return imageUrl;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){

        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){

        List<PmsProductSaleAttr> pmsProductSaleAttrList = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrList;
    }

    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId){

        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }

 }
