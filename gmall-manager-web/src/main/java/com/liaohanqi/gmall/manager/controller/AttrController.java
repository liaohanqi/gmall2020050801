package com.liaohanqi.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.PmsBaseAttrInfo;
import com.liaohanqi.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){

        List<PmsBaseAttrInfo> pmsBaseAttrInfolist = attrService.attrInfoList(catalog3Id);

        return pmsBaseAttrInfolist;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){

        attrService.saveAttrInfo(pmsBaseAttrInfo);

        return "success";
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public PmsBaseAttrInfo getAttrValueList(String attrId){

        PmsBaseAttrInfo pmsBaseAttrInfo = attrService.getAttrValueList(attrId);

        return pmsBaseAttrInfo;
    }



}
