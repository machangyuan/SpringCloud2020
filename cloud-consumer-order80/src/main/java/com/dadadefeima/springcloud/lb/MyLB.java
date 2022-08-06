package com.dadadefeima.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class MyLB implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger (0);

    public ServiceInstance instances(List<ServiceInstance> serviceInstanceList) {
        int index = getAndIncrement ( ) % serviceInstanceList.size ( );
        return serviceInstanceList.get (index);
    }

    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current = atomicInteger.get ();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet (current,next));
        System.out.println ("######第几次访问，次数next："+next );
        return next;
    }
}
