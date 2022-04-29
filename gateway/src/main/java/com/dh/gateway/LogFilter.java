package com.dh.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.logging.Logger;

@Component
public class LogFilter extends AbstractGatewayFilterFactory<LogFilter.Config> {

    private static Logger log = Logger.getLogger(LogFilter.class.getName());

    public LogFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config){
        return (exchange, chain) -> {
            //Filter before
            log.info("Path requested: " + exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //Filter after
                log.info("Time response: " + Calendar.getInstance().getTime());
            }));
        };
    }


    public static class Config {
        //Put the configuration properties

    }

}
