package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.PmsBaseAttrInfo;

import java.util.List;
import java.util.Set;

public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    PmsBaseAttrInfo getAttrValueList(String attrId);

    List<PmsBaseAttrInfo> getAttrValueListByValueIds(Set valueIdSet);
}
