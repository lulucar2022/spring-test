package cn.lulucar.quartz.job;

import cn.lulucar.quartz.service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName MyDynamicJob
 * @date 2025/2/5 10:30
 * @description
 */
@Slf4j
@Component
public class MyDynamicJob implements Job {
    @Resource
    private MyService myService;
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String param = jobExecutionContext.getMergedJobDataMap().getString("paramName");
        log.info("Executing job with parameter: {}", param);
        try {
            // 调用MyService中的方法
            myService.doSomething(param);
        } catch (Exception e) {
            log.error("Error executing job", e);
        }
    }
}
