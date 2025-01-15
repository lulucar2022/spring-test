package cn.lulucar.springbatch.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

/**
 * @author wenxiaolan
 * @ClassName JobErrorHandlingTest
 * @date 2025/1/15 13:53
 * @description
 */
@SpringBootTest
@SpringBatchTest
public class JobErrorHandlingTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Test
    void testJobErrorHandling() throws Exception {
        
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        // 打印任务执行信息
        System.out.println("Job Status: " + jobExecution.getStatus());
        System.out.println("Job Exit Status: " + jobExecution.getExitStatus());
        System.out.println("Step Executions: " + jobExecution.getStepExecutions());
        // 验证任务状态
        Assertions.assertEquals(BatchStatus.FAILED, jobExecution.getStatus());
        
        // 验证跳过的记录数
        StepExecution stepExecution = jobExecution.getStepExecutions()
                .stream()
                .filter(step -> step.getStepName().equals("step1"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Step1 execution not found"));
        
        Assertions.assertEquals(1, (stepExecution).getSkipCount(), "1 record should be skipped");
        
        Assertions.assertEquals(3, stepExecution.getRollbackCount(), "3 retries should be attempted");
    }
}
