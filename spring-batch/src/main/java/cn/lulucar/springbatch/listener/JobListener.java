package cn.lulucar.springbatch.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName JobListener
 * @date 2025/1/14 15:20
 * @description
 */
@Component
@Slf4j
@Data
public class JobListener implements JobExecutionListener {
    private boolean isStarted = false;
    private boolean isEnd = false;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        if ("true".equals(jobParameters.getString("forceFailure"))) {
            throw new RuntimeException("强制结束");
        }
        isStarted = true;
        log.info("Job start at: {}", jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        isEnd = true;
        log.info("Job end at: {}", jobExecution.getEndTime());
        log.info("Job status: {}", jobExecution.getStatus());
    }
}
