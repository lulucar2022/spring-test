package cn.lulucar.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * @author wenxiaolan
 * @ClassName MyJob
 * @date 2025/1/22 16:11
 * @description
 */

@Slf4j
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("executing job at:{}", jobExecutionContext.getFireTime());        
    }
}
