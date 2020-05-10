package com.liaohanqi.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.PmsProductInfo;
import com.liaohanqi.gmall.bean.PmsSkuInfo;
import com.liaohanqi.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){

        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }

}
