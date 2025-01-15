package cn.lulucar.springbatch.job.processor;

import cn.lulucar.springbatch.entity.Person;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wenxiaolan
 * @ClassName ItemProcessor
 * @date 2025/1/14 9:10
 * @description 对数据进行处理，小写转大写
 */
@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Getter
    public static final Set<String> threadNames = ConcurrentHashMap.newKeySet();
    @Override
    public Person process(Person person) throws Exception {
        String threadName = Thread.currentThread().getName();
        threadNames.add(threadName);
        log.info("Processing person: {} on thread: {}", person, Thread.currentThread().getName());
        // 模拟处理时发生异常：lastName为 Error时抛出异常
        if ("Error".equals(person.getLastName())) {
            throw new RuntimeException("Invalid last name: " + person.getLastName());
        }
        
        person.setFirstName(person.getFirstName().toUpperCase());
        person.setLastName(person.getLastName().toUpperCase());
        return person;
    }

}
