package cn.lulucar.springbatch.job.writer;

import cn.lulucar.springbatch.dao.PersonRepository;
import cn.lulucar.springbatch.entity.Person;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName FileItemWriter
 * @date 2025/1/15 11:08
 * @description
 */
@Component
public class FileItemWriter extends RepositoryItemWriter<Person> {
    @Autowired
    public FileItemWriter (PersonRepository personRepository) {
        setRepository(personRepository);
    }
}
