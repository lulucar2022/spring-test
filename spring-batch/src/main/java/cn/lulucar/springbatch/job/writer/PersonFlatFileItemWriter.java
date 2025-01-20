package cn.lulucar.springbatch.job.writer;

import cn.lulucar.springbatch.entity.Person;
import cn.lulucar.springbatch.resource.CsvResource;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName FileItemWriter
 * @date 2025/1/15 11:08
 * @description
 */
@Component
public class PersonFlatFileItemWriter extends FlatFileItemWriter<Person> {
    @Autowired
    private final CsvResource csvResource;
    
    @Autowired
    public PersonFlatFileItemWriter(CsvResource csvResource) {
        this.csvResource = csvResource;
        setResource(new FileSystemResource(this.csvResource.getFileSystemPath()));
        setAppendAllowed(true);
        
        // 设置行分隔符为逗号
        DelimitedLineAggregator<Person> lineAggregator = new DelimitedLineAggregator<>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                {
                    setNames(new String[]{"firstName", "lastName"});
                }
            });
        }};
        
        setLineAggregator(lineAggregator);
    }
}
