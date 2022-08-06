package com.dadadefeima.springcloud.controller;

import com.dadadefeima.springcloud.entities.CommonResult;
import com.dadadefeima.springcloud.entities.Payment;
import com.dadadefeima.springcloud.service.PaymentService;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value ("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    protected static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        LOGGER.info("插入结果"+result);

        if(result > 0){
            return new CommonResult(200,"插入成功,serverPort:"+serverPort, payment);
        }else {
            return new CommonResult (444,"插入失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById (id);
        LOGGER.info("查询结果"+payment+"热部署测试");

        if(payment != null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else {
            return new CommonResult (444,"查询失败，没有对应记录，查询ID：", payment);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public  Object getDiscovery(){
        List<String> services = discoveryClient.getServices ();
        for(String element : services){
            LOGGER.info ("-------element----"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances ("SPRINGCLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance : instances){
            LOGGER.info (instance.getServiceId ()+"\t"+instance.getHost ()+"\t"+instance.getPort ()+"\t"+instance.getUri ());
        }
        return this.discoveryClient;
    }

    @GetMapping(value="/payment/lb")
    public String getPaymentId(){
        return serverPort;
    }

    //Feign超时控制测试
    @GetMapping(value="/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep (3);
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
        return serverPort;
    }
}
