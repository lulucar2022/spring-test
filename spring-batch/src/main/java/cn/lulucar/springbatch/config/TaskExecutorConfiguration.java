package cn.lulucar.springbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wenxiaolan
 * @ClassName TaskExecutor
 * @date 2025/1/14 15:36
 * @description
 */
@Configuration
public class TaskExecutorConfiguration {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 空闲线程数
        executor.setMaxPoolSize(15);
        // 队列容量
        executor.setQueueCapacity(20);
        return executor;
    }
}
