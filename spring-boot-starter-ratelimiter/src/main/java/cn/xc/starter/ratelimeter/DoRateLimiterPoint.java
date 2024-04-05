package cn.xc.starter.ratelimeter;

import cn.xc.starter.ratelimeter.annotation.DoRateLimiter;
import cn.xc.starter.ratelimeter.factory.LimiteFactory;
import cn.xc.starter.ratelimeter.service.LimiterService;
import cn.xc.starter.ratelimeter.service.impl.RateLimiterService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 进入切面,让限流可以生效
 */
@Aspect
//@Component
public class DoRateLimiterPoint {

    private final LimiteFactory limiteFactory;

    public DoRateLimiterPoint(LimiteFactory limiteFactory) {
        this.limiteFactory = limiteFactory;
    }

    @Pointcut("@annotation(cn.xc.starter.ratelimeter.annotation.DoRateLimiter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(doRateLimiter)")
    public Object doRouter(ProceedingJoinPoint jp, DoRateLimiter doRateLimiter) throws Throwable {
        LimiterService valveService = limiteFactory.getLimiter(doRateLimiter.limiteType());
        return valveService.access(jp, getMethod(jp), doRateLimiter, jp.getArgs());
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
