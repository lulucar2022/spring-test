package cn.lulucar.springbatch.job.processor;

import cn.lulucar.springbatch.entity.Person;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author wenxiaolan
 * @ClassName ItemProcessor
 * @date 2025/1/14 9:10
 * @description 对数据进行处理，小写转大写
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        person.setFirstName(person.getFirstName().toUpperCase());
        person.setLastName(person.getLastName().toUpperCase());
        return person;
    }
}
