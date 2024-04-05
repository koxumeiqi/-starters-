# 白名单过滤-Starter

## 一、使用
使用只需要在对应Controller下方法上添加IsWhite注解，
然后填充key属性，它是用来指明方法对应的参数的名称，用于后续AOP拦截判断。
returnJson 用于不存在白名单时，返回的相关信息，为String类型...

## 二、实现
将配置的白名单名单注入到WhiteListProperties中(前缀为‘xc.white-list’,
类型为List< String >)，
然后自动装配进容器。

使用自定义注解+AOP的形式尝试对请求进行拦截，
获取到IsWhite注解上配置的信息，看是否在配置的白名单集合中，不在的话直接返回响应信息。