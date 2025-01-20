package cn.lulucar.springbatch.job.reader;

import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.resource.CsvResource;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName MultiResourceItemReader
 * @date 2025/1/16 17:09
 * @description
 */
@Component
public class PersonMultiResourceItemReader extends MultiResourceItemReader<Person> {
    private final CsvResource csvResource;
    
    public PersonMultiResourceItemReader(CsvResource csvResource) {
        this.csvResource = csvResource;
        setResources(this.csvResource.getCsvResource());
        FlatFileItemReader<Person> flatFileItemReader = new FlatFileItemReader<>() {{
            setLinesToSkip(1);
            setLineMapper(new DefaultLineMapper<Person>() {{
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setNames("firstname", "lastname");
                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }});
            }});
        }};
        setDelegate(flatFileItemReader);
    }
}
