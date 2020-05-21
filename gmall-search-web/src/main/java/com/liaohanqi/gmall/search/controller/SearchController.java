package com.liaohanqi.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.*;
import com.liaohanqi.gmall.service.AttrService;
import com.liaohanqi.gmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class SearchController {


    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("index")
    public String index(){

        return "index";
    }

    @RequestMapping("list.html")
    public String search(PmsSearchParam pmsSearchParam, ModelMap modelMap){

        //该方法已经完成以下：
        //1、根据入参判断是哪种搜索，三级Id/关键字/还是平台属性。
        // 然后转化为elasticsearch的dsl语句，相当于mysql的sql语句
        //2、elasticsearch客户端根据dsl执行相关的搜索操作，如显示结果，如高亮
        List<PmsSearchSkuInfo> pmssearchSkuInfos = searchService.search(pmsSearchParam);

        //以下是判断elasticsearch客户端执行完成后的结果是否为空
        //如果不为空怎么处理的问题
        if(pmssearchSkuInfos != null && pmssearchSkuInfos.size() > 0){

            Set valueIdSet = new HashSet();
            for (PmsSearchSkuInfo pmssearchSkuInfo : pmssearchSkuInfos) {
                List<PmsSkuAttrValue> skuAttrValueList = pmssearchSkuInfo.getSkuAttrValueList();
                for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                    String valueId = pmsSkuAttrValue.getValueId();
                    valueIdSet.add(valueId);
                }
            }
            List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueIds(valueIdSet);
            String[] valueIds = pmsSearchParam.getValueId();
            if (valueIds != null && valueIds.length > 0){

                //Crumb:面包屑
                ArrayList<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
                for (String valueId : valueIds) {
                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                    Iterator<PmsBaseAttrInfo> infoIterator = pmsBaseAttrInfos.iterator();
                    while (infoIterator.hasNext()){
                        PmsBaseAttrInfo pmsBaseAttrInfo = infoIterator.next();
                        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                            String pmsBaseAttrValueId = pmsBaseAttrValue.getId();
                            if (valueId.equals(pmsBaseAttrValueId)){

                                //制作面包屑
                                pmsSearchCrumb.setUrlParam(getUrlParam(pmsSearchParam,valueId));
                                pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                                pmsSearchCrumb.setValueId(valueId);
                                pmsSearchCrumbs.add(pmsSearchCrumb);

                                //删除已经配选择过的属性
                                infoIterator.remove();
                            }
                        }
                    }
                }
                modelMap.put("attrValueSelectedList",pmsSearchCrumbs);

            }
            modelMap.put("attrList",pmsBaseAttrInfos);
        }

        modelMap.put("urlParam",getUrlParam(pmsSearchParam));
        modelMap.put("skuLsInfoList",pmssearchSkuInfos);
        return "list";
    }

    private String getUrlParam(PmsSearchParam pmsSearchParam, String... valudIdForDelete) {

        String urlParam = "";
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueId1 = pmsSearchParam.getValueId();

        //如果传入的是三级Id
        if (StringUtils.isNotBlank(catalog3Id)){
            if (StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        //如果传入的是关键字
        if (StringUtils.isNotBlank(keyword)){
            if (StringUtils.isNotBlank(keyword)){
                urlParam = urlParam +"&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        //如果传入的是平台属性
        if (valueId1!=null &&valueId1.length >0){
            for (String valudId : valueId1) {
                if (valudIdForDelete == null || valudIdForDelete.length==0){
                    urlParam = urlParam + "&valueId="+valudId;
                }else {
                    if (!valudIdForDelete[0].equals(valudId)){
                        urlParam = urlParam + "&valueId=" + valudId;
                    }
                }
            }
        }


        return urlParam;
    }

}
