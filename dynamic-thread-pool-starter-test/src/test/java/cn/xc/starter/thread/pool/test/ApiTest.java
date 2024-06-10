package cn.xc.starter.thread.pool.test;


import cn.xc.starter.threadpool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ApiTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void test_publish_thread_pool_update_event() throws InterruptedException {
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity("dynamic-thread-pool-test-app", "threadPoolExecutor01");
        threadPoolConfigEntity.setCorePoolSize(100);
        threadPoolConfigEntity.setMaximumPoolSize(100);
        applicationContext.publishEvent(threadPoolConfigEntity);

        new CountDownLatch(1).await();
    }

}
