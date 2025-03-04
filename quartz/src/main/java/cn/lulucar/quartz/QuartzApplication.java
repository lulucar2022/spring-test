package cn.lulucar.quartz;

import cn.lulucar.quartz.service.JobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wxl
 */
@SpringBootApplication
public class QuartzApplication implements CommandLineRunner {
	private final JobService jobService;

	public QuartzApplication(JobService jobService) {
		this.jobService = jobService;
	}

	@Override
	public void run(String... args) throws Exception {
		// 初始化时调度一个作业作为示例
		jobService.scheduleJob("exampleJob", "group1",
				"exampleTrigger", "paramName", 
				"Hello, World!", 5);
	}
	public static void main(String[] args) {
		SpringApplication.run(QuartzApplication.class, args);
	}

}
