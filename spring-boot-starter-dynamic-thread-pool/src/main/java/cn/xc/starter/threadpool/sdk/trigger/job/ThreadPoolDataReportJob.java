package cn.xc.starter.threadpool.sdk.trigger.job;


import cn.xc.starter.threadpool.sdk.domain.IDynamicThreadPoolService;
import cn.xc.starter.threadpool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.xc.starter.threadpool.sdk.registry.IRegistry;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class ThreadPoolDataReportJob {

    private Logger log = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void reportThreadPoolData() {
        List<ThreadPoolConfigEntity> threadPoolConfigList = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigList);
        log.info("动态线程池，上报线程池信息：{}", JSON.toJSONString(threadPoolConfigList));
        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigList) {
            registry.reportThreadPoolConfigParameter(dynamicThreadPoolService.queryThreadPoolConfigByName(threadPoolConfigEntity.getThreadPoolName()));
            log.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
        }
    }

}
