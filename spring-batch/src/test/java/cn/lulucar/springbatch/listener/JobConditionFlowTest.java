package cn.lulucar.springbatch.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wenxiaolan
 * @ClassName JobConditionFlowTest
 * @date 2025/1/15 11:19
 * @description
 */
@SpringBootTest
@SpringBatchTest
public class JobConditionFlowTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Test
    void testStep1Failure() throws Exception {
        // 模拟 step1 失败
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("forceFailure", "true")
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        // 打印任务执行信息
        System.out.println("Job Status: " + jobExecution.getStatus());
        System.out.println("Job Exit Status: " + jobExecution.getExitStatus());
        System.out.println("Step Executions: " + jobExecution.getStepExecutions());
        // 验证任务状态
        Assertions.assertEquals(BatchStatus.FAILED, jobExecution.getStatus());
        
        // 验证 step2 是否未执行
        StepExecution step2 = jobExecution.getStepExecutions()
                .stream()
                .filter(step -> step.getStepName().equals("step2"))
                .findFirst()
                .orElse(null);

        Assertions.assertNull(step2, "step2 未执行");
    }
}
