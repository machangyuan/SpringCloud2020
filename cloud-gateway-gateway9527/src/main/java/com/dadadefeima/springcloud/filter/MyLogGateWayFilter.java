package com.dadadefeima.springcloud.filter;

import com.dadadefeima.springcloud.config.GateWayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    protected static final Logger LOGGER = LoggerFactory.getLogger(MyLogGateWayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //ServerWebExchange类型于request，response；GatewayFilterChain过滤器链
        LOGGER.info ("*******come in MyGatewayFilter"+new Date ());
        String uname = exchange.getRequest ( ).getQueryParams ( ).getFirst ("uname");
        if(null == uname){
            LOGGER.info ("#######用户名非法");
            exchange.getResponse ().setStatusCode (HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse ().setComplete ();
        }
        return chain.filter (exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
