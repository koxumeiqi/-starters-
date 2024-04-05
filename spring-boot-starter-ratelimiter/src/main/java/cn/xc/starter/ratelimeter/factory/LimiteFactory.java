package cn.xc.starter.ratelimeter.factory;

import cn.xc.starter.ratelimeter.LimiteType;
import cn.xc.starter.ratelimeter.service.LimiterService;
import cn.xc.starter.ratelimeter.service.impl.RateLimiterService;


/**
 * 限流工厂,对限流的选择对外提供
 */
public class LimiteFactory {

    public LimiterService getLimiter(LimiteType limiteType) {
        if (limiteType.equals(LimiteType.RATE_LIMITER)) {
            return new RateLimiterService();
        } else {
            return new RateLimiterService();
        }
    }

}
