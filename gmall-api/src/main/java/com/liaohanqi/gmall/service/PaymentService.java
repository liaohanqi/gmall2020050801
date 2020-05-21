package com.liaohanqi.gmall.service;

import com.liaohanqi.gmall.bean.PaymentInfo;

public interface PaymentService {
    void sava(PaymentInfo paymentInfo);

    void update(PaymentInfo paymentInfo);

    void sendPaySuccessQueue(PaymentInfo paymentInfo);

    void sendPayCheckQueue(PaymentInfo paymentInfo, Long count);
}
