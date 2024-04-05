package cn.xc.starter.whitelist.config;


import cn.xc.starter.whitelist.WhiteListAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(WhiteListProperties.class)
@ConditionalOnClass(WhiteListProperties.class) // 如果 WhiteListProperties 类存在，则执行下面的配置
public class WhiteListAutoConfig {

    @Bean("whiteListConfig")
    @ConditionalOnMissingBean // 如果容器中不存在 WhiteListProperties 类型的 Bean，则创建该 Bean
    public List<String> whiteListConfig(WhiteListProperties properties) {
        return properties.getUsers();
    }

    @Bean
    public WhiteListAspect whiteListAspect(List<String> whiteListConfig) {
        return new WhiteListAspect(whiteListConfig);
    }

}
