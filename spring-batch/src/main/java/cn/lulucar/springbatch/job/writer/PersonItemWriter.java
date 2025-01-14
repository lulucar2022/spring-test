package cn.lulucar.springbatch.job.writer;

import cn.lulucar.springbatch.dao.PersonRepository;
import cn.lulucar.springbatch.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName PersonItemWriter
 * @date 2025/1/14 9:13
 * @description
 */
@Component
public class PersonItemWriter extends RepositoryItemWriter<Person> {
    @Autowired
    public PersonItemWriter(PersonRepository personRepository) {
        setRepository(personRepository);
        
    }
}
