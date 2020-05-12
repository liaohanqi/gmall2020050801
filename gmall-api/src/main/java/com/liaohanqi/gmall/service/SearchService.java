package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.PmsSearchParam;
import com.liaohanqi.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> search(PmsSearchParam pmsSearchParam);
}
