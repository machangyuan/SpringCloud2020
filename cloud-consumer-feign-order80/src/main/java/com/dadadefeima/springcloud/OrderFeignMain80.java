package com.dadadefeima.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients//想要使用feign，先在主启动类进行激活并开启，Enable
public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run (OrderFeignMain80.class,args);
    }
}
