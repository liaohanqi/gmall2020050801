package com.liaohanqi.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.PmsBaseCatalog1;
import com.liaohanqi.gmall.bean.PmsBaseCatalog2;
import com.liaohanqi.gmall.bean.PmsBaseCatalog3;
import com.liaohanqi.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){

        return "index";
    }


    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){

        List<PmsBaseCatalog1> pmsBaseCatalog1List= catalogService.getCatalog1();

        return pmsBaseCatalog1List;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){

        List<PmsBaseCatalog2> pmsBaseCatalog1List2= catalogService.getCatalog2(catalog1Id);

        return pmsBaseCatalog1List2;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){

        List<PmsBaseCatalog3> pmsBaseCatalog2List = catalogService.getCatalog3(catalog2Id);

        return pmsBaseCatalog2List;
    }


}
