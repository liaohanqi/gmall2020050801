package com.liaohanqi.gmall.bean;

import java.io.Serializable;

public class Details implements Serializable {

    private String skuId;
    private String skuNum;
    private String skuName;

    @Override
    public String toString() {
        return "Details{" +
                "skuId='" + skuId + '\'' +
                ", skuNum='" + skuNum + '\'' +
                ", skuName='" + skuName + '\'' +
                '}';
    }

    public Details() {
    }

    public Details(String skuId, String skuNum, String skuName) {
        this.skuId = skuId;
        this.skuNum = skuNum;
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
