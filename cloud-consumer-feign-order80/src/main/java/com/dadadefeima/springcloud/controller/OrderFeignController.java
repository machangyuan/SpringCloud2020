package com.dadadefeima.springcloud.controller;

import com.dadadefeima.springcloud.entities.CommonResult;
import com.dadadefeima.springcloud.entities.Payment;
import com.dadadefeima.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        CommonResult<Payment> payment = paymentFeignService.getPaymentById (id);
        return payment;
    }


    @GetMapping(value="/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(@PathVariable("id") Long id){
        //openfeign-ribbon,客户端一般默认等待1秒钟
        return paymentFeignService.paymentFeignTimeout (id);
    }
}
