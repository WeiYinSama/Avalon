package net.avalon.dynamicsql.datasource;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot3.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

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
        int minIdle = source.getMinIdle();
        log.info("minIdle: {}", minIdle);

        String driverClassName = source.getDriverClassName();
        log.info("driverClassName: {}",driverClassName);
        String url = source.getUrl();
        log.info("url: {}", url);
        String username = source.getUsername();
        log.info("username: {}", username);
        String password = source.getPassword();
        log.info("password: {}", password);
        String validationQuery = source.getValidationQuery();
        log.info("validationQuery: {}", validationQuery);
    }

    @Autowired
    private ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean;


    @Test
    void t3() {

    }
}
