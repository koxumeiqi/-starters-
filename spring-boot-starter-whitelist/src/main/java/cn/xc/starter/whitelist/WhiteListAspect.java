package cn.xc.starter.whitelist;

import cn.xc.starter.whitelist.annotation.IsWhite;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
//@Component
public class WhiteListAspect {

    private Logger logger = LoggerFactory.getLogger(WhiteListAspect.class);

    private List<String> whiteListConfig;

    public WhiteListAspect(List<String> whiteListConfig) {
        this.whiteListConfig = whiteListConfig;
    }

    @Pointcut("@annotation(cn.xc.starter.whitelist.annotation.IsWhite)")
    public void whiteListPointCut() {
    }

    @Around("whiteListPointCut()")
    public Object whiteListAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        IsWhite isWhite = method.getAnnotation(IsWhite.class);
        String fieldName = isWhite.key();
        // 获取字段值
        String keyValue = getFiledValue(fieldName, joinPoint.getArgs());
        logger.info("middleware whitelist handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return joinPoint.proceed();
        // 白名单过滤
        if (whiteListConfig.contains(keyValue)) {
            return joinPoint.proceed();
        }
        // 拦截
        return returnObject(isWhite, method);
    }

    // 返回对象
    private Object returnObject(IsWhite whiteList, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    // 获取属性值
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }


}
