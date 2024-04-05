package cn.xc.starter.whitelist.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用于后续判断是否是白名单里的成员
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsWhite {

    String key() default ""; // 字段名称

    String returnJson() default "";

}
