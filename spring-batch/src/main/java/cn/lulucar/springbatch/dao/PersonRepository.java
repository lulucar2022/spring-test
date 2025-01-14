package cn.lulucar.springbatch.dao;

import cn.lulucar.springbatch.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wenxiaolan
 * @ClassName PersonRepository
 * @date 2025/1/14 8:55
 * @description 继承 jpaRepository 来完成对数据库的操作
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
