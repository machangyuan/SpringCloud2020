package com.dadadefeima.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//更换ribbon策略规则为随机，默认是的轮询
@Configuration
public class MyselfRule {
    @Bean
    public IRule muRule(){
        return new RandomRule ();
    }
}
