package net.avalon.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
@Slf4j
class JdbcApplicationTests {

    @Test
    void contextLoads() throws Exception{

        String url = "jdbc:mysql://localhost:3306/ciallo";
        String username = "root";
        String password = "Hd15364514749+";

        // 建立一个到指定数据库的连接，并返回一个Connection对象
        Connection conn = DriverManager.getConnection(url, username, password);
        // 创建一个 statement，用以执行不带参数的SQL查询和更新
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from comic");
        while (resultSet.next()){
            log.info("comic：{}", resultSet.getString("name"));
        }
        conn.close();
    }

}
