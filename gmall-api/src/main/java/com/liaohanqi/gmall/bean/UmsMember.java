package com.liaohanqi.gmall.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UmsMember implements Serializable {

    String id;
    String memberLevelId;
    String username;
    String password;
    String nickname;
    String phone;
    String status;
    Date createTime;
    String icon;
    String gender;
    Date birthday;
    String city;
    String job;
    String personalizedSignature;
    String source_uid;
    BigDecimal sourceType;
    String integration;
    String growth;
    String luckeyCount;
    String accessToken;
    String accessCode;
    BigDecimal historyIntegration;


}
