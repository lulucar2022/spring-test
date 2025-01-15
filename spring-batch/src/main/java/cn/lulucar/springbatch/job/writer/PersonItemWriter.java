package cn.lulucar.springbatch.job.writer;

import cn.lulucar.springbatch.dao.PersonRepository;
import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.job.processor.PersonItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wenxiaolan
 * @ClassName PersonItemWriter
 * @date 2025/1/14 9:13
 * @description
 */
@Component
@Slf4j
public class PersonItemWriter extends RepositoryItemWriter<Person> {
    @Autowired
    public PersonItemWriter(PersonRepository personRepository) {
        setRepository(personRepository);
        
    }

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {
        String threadName = Thread.currentThread().getName();
        PersonItemProcessor.getThreadNames().add(threadName);
        log.info("Writing {} persons to database on thread: {}", chunk.size(), Thread.currentThread().getName());
        super.write(chunk);
    }
}
