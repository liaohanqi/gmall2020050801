package com.liaohanqi.gmall.manager.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liaohanqi.gmall.bean.PmsBaseCatalog1;
import com.liaohanqi.gmall.bean.PmsBaseCatalog2;
import com.liaohanqi.gmall.bean.PmsBaseCatalog3;
import com.liaohanqi.gmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.liaohanqi.gmall.manager.mapper.PmsBaseCatalog1Mapper2;
import com.liaohanqi.gmall.manager.mapper.PmsBaseCatalog1Mapper3;
import com.liaohanqi.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog1Mapper2 pmsBaseCatalog1Mapper2;

    @Autowired
    PmsBaseCatalog1Mapper3 pmsBaseCatalog1Mapper3;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {

        return pmsBaseCatalog1Mapper.selectAll();

    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {

        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();

        pmsBaseCatalog2.setCatalog1Id(catalog1Id);

        List<PmsBaseCatalog2> pmsBaseCatalog2s = pmsBaseCatalog1Mapper2.select(pmsBaseCatalog2);

        return pmsBaseCatalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {

        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();

        pmsBaseCatalog3.setCatalog2Id(catalog2Id);

        List<PmsBaseCatalog3> pmsBaseCatalog3s = pmsBaseCatalog1Mapper3.select(pmsBaseCatalog3);

        return pmsBaseCatalog3s;
    }
}
