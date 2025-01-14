package cn.lulucar.springbatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenxiaolan
 * @ClassName BatchController
 * @date 2025/1/14 11:17
 * @description
 */
@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job helloJob;
    
    @GetMapping("/person")
    public String personBatchJob() throws Exception {
        // 创建 jobParameters, 确保每次调用都是唯一的
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        // 启动批处理任务
        jobLauncher.run(helloJob,jobParameters);
        
        return "Batch Job Started!";
    }
}
