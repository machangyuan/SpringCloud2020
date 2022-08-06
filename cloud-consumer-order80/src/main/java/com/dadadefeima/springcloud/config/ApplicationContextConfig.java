package com.dadadefeima.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    //@LoadBalanced//Ribbon @LoadBalanced注解赋予RestTemplate负载均衡的能力 把@LoadBalanced去掉的目的是，手写一个负载均衡的算法
    public RestTemplate getRestTemplate(){
        //这里相当于applicationContext.xml <bean id="" class="">
        //ioc容器里面放入了RestTemplate对象
        return new RestTemplate ();
    }


}
