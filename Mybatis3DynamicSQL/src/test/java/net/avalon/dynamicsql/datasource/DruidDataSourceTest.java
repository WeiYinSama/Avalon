package net.avalon.dynamicsql.datasource;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

/**
 * @Author: Heda
 * @Create: 2024/8/16
 */
@SpringBootTest
@Slf4j
public class DruidDataSourceTest {


    @Autowired
    private DataSource dataSource;


    @Test
    void t1() {
        log.info("dataSource Class: " + dataSource.getClass());
    }

    @Test
    void t2() {
        DruidDataSourceWrapper source = (DruidDataSourceWrapper) dataSource;
        //
        int initialSize = source.getInitialSize();
        log.info("initialSize: " + initialSize);
        int maxActive = source.getMaxActive();
        log.info("maxActive: {}",maxActive);

        String driverClassName = source.getDriverClassName();
        log.info("driverClassName: {}",driverClassName);
        String url = source.getUrl();
        log.info("url: {}", url);
        String username = source.getUsername();
        log.info("username: {}", username);
        String password = source.getPassword();
        log.info("password: {}", password);
    }
}
