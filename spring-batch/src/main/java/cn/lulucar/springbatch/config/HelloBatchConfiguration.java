package cn.lulucar.springbatch.config;

import cn.lulucar.springbatch.dao.PersonRepository;
import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.job.processor.PersonItemProcessor;
import cn.lulucar.springbatch.job.reader.PersonItemReader;
import cn.lulucar.springbatch.job.writer.PersonItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
    public Job helloJob(JobRepository jobRepository, Step step){
        return new JobBuilder("helloJob", jobRepository)
                .start(step)
                .build();
    }
    
    @Bean
    public Step helloStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                          ItemReader<Person> itemReader, ItemProcessor<Person, Person> itemProcessor,
                          ItemWriter<Person> itemWriter) {
        return new StepBuilder("helloStep", jobRepository)
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
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }
    
    @Bean
    public ItemWriter<Person> itemWriter(PersonRepository personRepository) {
        return new PersonItemWriter(personRepository);
    }
}
