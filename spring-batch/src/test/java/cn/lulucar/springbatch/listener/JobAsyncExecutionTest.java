package cn.lulucar.springbatch.listener;

import cn.lulucar.springbatch.job.processor.PersonItemProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wenxiaolan
 * @ClassName JobAsyncExecutionTest
 * @date 2025/1/15 16:24
 * @description
 */
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
public class JobAsyncExecutionTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Test
    void testJobAsyncExecution() throws Exception {
        // 启动任务
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        
        // 验证任务状态
        Assertions.assertEquals(BatchStatus.FAILED, jobExecution.getStatus());

        // 获取记录的线程名称
        Set<String> threadNames = PersonItemProcessor.getThreadNames();

        // 验证是否使用了多个线程
        Assertions.assertTrue(threadNames.size() > 1, "Task should be executed on multiple threads");

        // 打印线程名称
        threadNames.forEach(threadName -> System.out.println("Thread used: " + threadName));
        
    }
}
