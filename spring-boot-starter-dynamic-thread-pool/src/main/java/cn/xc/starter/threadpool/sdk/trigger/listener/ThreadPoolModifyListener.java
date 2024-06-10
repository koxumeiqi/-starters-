package cn.xc.starter.threadpool.sdk.trigger.listener;

import cn.xc.starter.threadpool.sdk.domain.DynamicThreadPoolService;
import cn.xc.starter.threadpool.sdk.domain.IDynamicThreadPoolService;
import cn.xc.starter.threadpool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.xc.starter.threadpool.sdk.registry.IRegistry;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.List;

public class ThreadPoolModifyListener {

    private final Logger log = LoggerFactory.getLogger(ThreadPoolModifyListener.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;

    public ThreadPoolModifyListener(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @EventListener
    public void onThreadPoolModify(ThreadPoolConfigEntity threadPoolConfigEntity) {
        log.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}", threadPoolConfigEntity.getThreadPoolName(), threadPoolConfigEntity.getPoolSize(), threadPoolConfigEntity.getMaximumPoolSize());
        dynamicThreadPoolService.updateThreadPoolConfig(threadPoolConfigEntity);

        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);

        ThreadPoolConfigEntity threadPoolConfigUpdated = dynamicThreadPoolService.queryThreadPoolConfigByName(threadPoolConfigEntity.getThreadPoolName());
        registry.reportThreadPoolConfigParameter(threadPoolConfigUpdated);
        log.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
    }

}
