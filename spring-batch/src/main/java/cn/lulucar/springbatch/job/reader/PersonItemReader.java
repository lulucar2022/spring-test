package cn.lulucar.springbatch.job.reader;

import cn.lulucar.springbatch.entity.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName PersonItemReader
 * @date 2025/1/14 9:04
 * @description 数据读取
 */
@Component
public class PersonItemReader extends FlatFileItemReader<Person> {
    public PersonItemReader() {
        //读取 csv 文件
        setResource(new ClassPathResource("input/person.csv"));   
        setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("firstName", "lastName"); // 列名
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Person.class);    // 映射到 person 类
            }});
        }});
    }
}
