package cn.xc.starter.ratelimeter.service;

import cn.xc.starter.ratelimeter.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public interface LimiterService {
    Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable;
}
