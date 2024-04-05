package cn.xc.starter.ratelimeter.service.impl;

import cn.xc.starter.ratelimeter.Constants;
import cn.xc.starter.ratelimeter.annotation.DoRateLimiter;
import cn.xc.starter.ratelimeter.service.LimiterService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * 通过令牌桶算法进行限流：以每秒令牌数的方式进行限流
 */
public class RateLimiterService implements LimiterService {

    private final Logger log = Logger.getLogger(RateLimiterService.class.getName());

    @Override
    public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        // 判断是否开启
        if (0 == doRateLimiter.permitsPerSecond()) return jp.proceed();

        String clazzName = jp.getTarget().getClass().getName();
        String methodName = method.getName();

        String key = clazzName + "." + methodName; // 这主要是对一个方法进行限流

        if (null == Constants.rateLimiterMap.get(key)) {
            Constants.rateLimiterMap.put(key, RateLimiter.create(doRateLimiter.permitsPerSecond()));
        }

        RateLimiter rateLimiter = Constants.rateLimiterMap.get(key);
        if (rateLimiter.tryAcquire()) { // 这里尝试进行获取令牌
            return jp.proceed();
        }

        log.info("被限流处理了,此时流量超过了设置的范围...");


        if (method.getReturnType().equals(String.class) && doRateLimiter.returnJson().length() < 1) {
            return "系统繁忙,请稍后重试";
        }

        return JSON.parseObject(doRateLimiter.returnJson(), method.getReturnType());
    }

}
