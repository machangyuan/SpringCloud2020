package com.dadadefeima.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    public String payment_ok(Integer id) {
        return "---------PaymentFallbackService fall back payment_ok,哭";
    }

    public String payment_timeout(Integer id) {
        return "---------PaymentFallbackService fall back payment_timeout,哭";
    }
}
