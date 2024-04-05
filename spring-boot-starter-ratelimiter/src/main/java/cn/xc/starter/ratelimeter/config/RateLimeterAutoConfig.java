package cn.xc.starter.ratelimeter.config;


import cn.xc.starter.ratelimeter.DoRateLimiterPoint;
import cn.xc.starter.ratelimeter.factory.LimiteFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RateLimeterAutoConfig {

    @Bean
    public DoRateLimiterPoint rateLimiterPoint(LimiteFactory limiteFactory) {
        return new DoRateLimiterPoint(limiteFactory);
    }

    @Bean
    public LimiteFactory limiteFactory() {
        return new LimiteFactory();
    }

}
