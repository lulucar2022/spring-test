package cn.lulucar.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wenxiaolan
 * @ClassName MyService
 * @date 2025/2/5 10:37
 * @description
 */
@Slf4j
@Service
public class MyService {
    public void doSomething(String param) {
      log.info("doing something with parameter: {}",param);  
    }
}
