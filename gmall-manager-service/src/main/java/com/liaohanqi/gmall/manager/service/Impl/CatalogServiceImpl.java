package com.liaohanqi.gmall.manager.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liaohanqi.gmall.bean.PmsBaseCatalog1;
import com.liaohanqi.gmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.liaohanqi.gmall.service.CatalogService;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {

    @Reference
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {

        return pmsBaseCatalog1Mapper.selectAll();

    }
}
