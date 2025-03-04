package cn.lulucar.quartz.service;

import cn.lulucar.quartz.job.MyDynamicJob;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * @author wenxiaolan
 * @ClassName JobService
 * @date 2025/2/5 11:01
 * @description
 */
@Service
public class JobService {
    private final SchedulerFactoryBean schedulerFactoryBean;

    public JobService(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    public void scheduleJob(String jobName, String groupName, String triggerName, String paramName, String paramValue, int intervalInSeconds) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(MyDynamicJob.class)
                .withIdentity(jobName, groupName)
                .usingJobData(paramName, paramValue)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever()
                        .withIntervalInSeconds(intervalInSeconds))
                .build();
        
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
