package com.liaohanqi.gmall.manager.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liaohanqi.gmall.bean.PmsBaseAttrInfo;
import com.liaohanqi.gmall.bean.PmsBaseAttrValue;
import com.liaohanqi.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.liaohanqi.gmall.manager.mapper.PmsBaseAttrValueMapper;
import com.liaohanqi.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {

        //为了显示出平台属性的字段（field）
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);

        //为了显示出平台属性的值（value）
        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos) {
            String id = baseAttrInfo.getId();

            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(id);
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);

        }

        return pmsBaseAttrInfos;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {

        String attrId = "";

        //判断平台属性是否存在值（value）
        if(StringUtils.isNotBlank(pmsBaseAttrInfo.getId())){
            //存在，就修改平台属性
            attrId = pmsBaseAttrInfo.getId();
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValue);

        }else {
            //不存在，就保存
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            attrId = pmsBaseAttrInfo.getId();
        }
        //判断平台的属性Id是否存在，就添加平台属性
        if (StringUtils.isNotBlank(attrId)){

            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(attrId);
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);

            }
        }

    }    @Override
    public PmsBaseAttrInfo getAttrValueList(String attrId) {

        //显示出平台属性的字段
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setId(attrId);
        PmsBaseAttrInfo pmsBaseAttrInfo1 = pmsBaseAttrInfoMapper.selectOne(pmsBaseAttrInfo);

        //显示出平台属性的属性值
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        pmsBaseAttrInfo1.setAttrValueList(pmsBaseAttrValues);
        return pmsBaseAttrInfo1;

    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueIds(Set valueIdSet) {

        String join = StringUtils.join(valueIdSet, ",");
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrValueMapper.selectAttrValueListByValueIds(join);
        return pmsBaseAttrInfos;
    }

}
