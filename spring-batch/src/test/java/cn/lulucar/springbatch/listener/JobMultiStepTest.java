package cn.lulucar.springbatch.listener;

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

import java.util.List;
import java.util.Objects;

/**
 * @author wenxiaolan
 * @ClassName JobMultiStepTest
 * @date 2025/1/15 11:35
 * @description
 */
@SpringBootTest
@SpringBatchTest
public class JobMultiStepTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Test
    void testJobMultiStep() throws Exception {
        // 启动任务
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        // 打印任务执行信息
        System.out.println("Job Status: " + jobExecution.getStatus());
        System.out.println("Job Exit Status: " + jobExecution.getExitStatus());
        System.out.println("Step Executions: " + jobExecution.getStepExecutions());
        // 验证任务
        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        
        // 验证 step1 和 step2 是否按顺序执行
        List<StepExecution> stepExecutionList = jobExecution.getStepExecutions().stream().toList();
        
        Assertions.assertEquals(2, stepExecutionList.size(), "两个步骤应该是大小为2");
        
        StepExecution step1Execution = stepExecutionList.get(0);
        StepExecution step2Execution = stepExecutionList.get(1);
        Assertions.assertEquals("step1", step1Execution.getStepName());
        Assertions.assertEquals("step2", step2Execution.getStepName());
        
        Assertions.assertTrue(Objects.requireNonNull(step1Execution.getStartTime()).isBefore(step2Execution.getStartTime()));
        Assertions.assertTrue(Objects.requireNonNull(step1Execution.getEndTime()).isBefore(step2Execution.getStartTime()));
        
    }
}
