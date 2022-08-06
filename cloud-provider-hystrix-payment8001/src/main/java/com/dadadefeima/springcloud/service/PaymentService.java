package com.dadadefeima.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问，肯定ok
     * @param id
     * @return
     */
   public String payment_ok(Integer id){
       return "线程池： "+Thread.currentThread ().getName ()+"  payment_ok,id: "+id+"\t"+"哈哈";
   }

   @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
           @HystrixProperty (name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
   })
    public String payment_timeout(Integer id){
       int timeNum = 3;
       try {
            TimeUnit.SECONDS.sleep (timeNum);
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
       //int age = 10/0;运行异常
        return "线程池： "+Thread.currentThread ().getName ()+"  payment_timeout,id: "+id+"\t"+"哈哈"+"  耗时(秒):"+timeNum;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread ().getName ()+"  8001系统繁忙或者运行报错，请稍后再试,id: "+id+"\t"+"哭";
    }

    //======服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty (name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty (name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty (name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty (name="circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸，10次里面有60%以上的错误，也就是6次以上的错误
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
       if(id<0){
           throw new RuntimeException ("---------id 不能为负数----");
       }
       String serialNum = IdUtil.simpleUUID ();
       return Thread.currentThread ().getName ()+"\t"+"调用成功，流水号："+serialNum;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
       return "id 不能为负数，请稍后再试，id："+id;
    }


}
