package cn.lulucar.springbatch.config;

import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.job.processor.PersonItemProcessor;
import cn.lulucar.springbatch.job.reader.PersonMultiResourceItemReader;
import cn.lulucar.springbatch.job.writer.PersonFlatFileItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author wenxiaolan
 * @ClassName CsvBatchConfiguration
 * @date 2025/1/16 17:57
 * @description 多个csv文件输出到一个csv文件
 */
@Configuration
@Slf4j
public class CsvBatchConfiguration {
    @Bean
    public Job csvJob(JobRepository jobRepository, Step csvStep) {
        return new JobBuilder("csvJob", jobRepository)
                .start(csvStep)
                .build();
    }
    @Bean
    public Step csvStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                        PersonMultiResourceItemReader multiResourceItemReader,
                        PersonItemProcessor itemProcessor,
                        PersonFlatFileItemWriter flatFileItemWriter) {
        return new StepBuilder("csvStep", jobRepository)
                .<Person, Person>chunk(5,platformTransactionManager)
                .reader(multiResourceItemReader)
                .processor(itemProcessor)
                .writer(flatFileItemWriter)
                .build();
                
    }
    
}
