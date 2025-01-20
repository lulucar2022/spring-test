package cn.lulucar.springbatch.config;

import cn.lulucar.springbatch.dao.PersonRepository;
import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.job.reader.PersonItemReader;
import cn.lulucar.springbatch.job.writer.PersonItemWriter;
import cn.lulucar.springbatch.listener.JobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author wenxiaolan
 * @ClassName HelloConfiguration
 * @date 2025/1/13 10:45
 * @description
 */
@Configuration
@Slf4j
public class HelloBatchConfiguration {
    
    @Bean
    public Job helloJob(JobRepository jobRepository, Step step1, Step step2, JobListener jobListener){
        return new JobBuilder("helloJob", jobRepository)
                .listener(jobListener)
                .start(step1)
                .on("FAILED").end()
                .from(step1).on("*").to(step2)
                .end()
                .build();
    }
    
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      @Qualifier("personItemReader") ItemReader<Person> itemReader, ItemProcessor<Person, Person> itemProcessor,
                      @Qualifier("personItemWriter") ItemWriter<Person> itemWriter, TaskExecutor taskExecutor) {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor)
                .faultTolerant()
                // 最多跳过 10 条错误数据
                .skipLimit(10)
                // 跳过所有异常
                .skip(Exception.class)
                // 最多重试 3 次
                .retryLimit(3)
                // 重试所有异常
                .retry(Exception.class)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      @Qualifier("personItemReader") ItemReader<Person> itemReader, ItemProcessor<Person, Person> itemProcessor,
                      @Qualifier("personItemWriter") ItemWriter<Person> itemWriter) {
        return new StepBuilder("step2", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }
    
    @Bean
    public ItemReader<Person> reader() {
        return new PersonItemReader();
    }
    
    
    @Bean
    public ItemWriter<Person> itemWriter(PersonRepository personRepository) {
        return new PersonItemWriter(personRepository);
    }

    
}
