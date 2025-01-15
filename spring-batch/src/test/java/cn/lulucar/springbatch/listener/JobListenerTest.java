package cn.lulucar.springbatch.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


/**
 * @author wenxiaolan
 * @ClassName JobListenerTest
 * @date 2025/1/15 9:56
 * @description
 */
@SpringBatchTest
@SpringBootTest
public class JobListenerTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Autowired
    private JobListener jobListener;
    
    @Test
    void testJobListener() throws Exception{
        // 启动任务
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // 验证任务状态
        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // 验证监听器是否记录了任务开始和结束时间
        Assertions.assertTrue(jobListener.isStarted(), "作业应该开始了");
        Assertions.assertTrue(jobListener.isEnd(), "作业应该结束了");
        
    }
}