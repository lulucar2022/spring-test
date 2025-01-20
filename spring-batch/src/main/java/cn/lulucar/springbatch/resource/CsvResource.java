package cn.lulucar.springbatch.resource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author wenxiaolan
 * @ClassName CsvResource
 * @date 2025/1/17 18:00
 * @description
 */
@Data
@Component
public class CsvResource {
    @Value("classpath:input/person*.csv")
    private Resource[] csvResource;

    /**
     * 使用在文件系统中
     */
    private String fileSystemPath = "E:\\filedemo\\output.csv"; 

}
