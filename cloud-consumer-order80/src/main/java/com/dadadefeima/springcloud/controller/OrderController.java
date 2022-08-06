package com.dadadefeima.springcloud.controller;

import com.dadadefeima.springcloud.entities.CommonResult;
import com.dadadefeima.springcloud.entities.Payment;
import com.dadadefeima.springcloud.lb.LoadBalancer;
import com.dadadefeima.springcloud.lb.MyLB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class OrderController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://SPRINGCLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject (PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject (PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    //自己手写负载均衡轮询算法
    @GetMapping(value="/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instanceList = discoveryClient.getInstances ("SPRINGCLOUD-PAYMENT-SERVICE");
        if(null == instanceList || instanceList.size () <= 0){
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances (instanceList);
        URI url = serviceInstance.getUri ();
        return restTemplate.getForObject (url+"/payment/lb",String.class);
    }

//    @GetMapping(value="/consumer/payment/lb")
//    public String getPaymentLB(){
//        return restTemplate.getForObject (PAYMENT_URL+"/payment/lb",String.class);
//    }

}
