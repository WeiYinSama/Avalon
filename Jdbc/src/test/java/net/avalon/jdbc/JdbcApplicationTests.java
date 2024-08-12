package net.avalon.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

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
        // 默认情况下，数据库连接处于自动提交模式
//        conn.setAutoCommit(false);
//        conn.commit();
//        conn.rollback();

        // 创建一个 statement，用以执行不带参数的SQL查询和更新
//        Statement statement = conn.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from comic");
//        while (resultSet.next()){
//            log.info("comic：{}", resultSet.getString("name"));
//        }

        // 使用预备语句查询
//        String sql = "select * from comic where name like ?";
//        String name = "%的%";
//        PreparedStatement stat = conn.prepareStatement(sql);
//        stat.setString(1,name);
//
//        ResultSet rs = stat.executeQuery();
//        while (rs.next()){
//            log.info("comic：{}", rs.getString("name"));
//        }

        // 联表查询
//        String sql = "select comic.name,category.name from comic join category on comic.category_id = category.id where comic.type = ?";
//        byte type = 1;
//        PreparedStatement stat = conn.prepareStatement(sql);
//        stat.setByte(1,type);
//
//        ResultSet rs = stat.executeQuery();
//        while (rs.next()){
//            log.info("comic：{}, category：{}", rs.getString("comic.name"), rs.getString(2));
//        }

        // 元数据
//        DatabaseMetaData meta = conn.getMetaData();
//        ResultSet mrs = meta.getTables(null, null, null, new String[]{"TABLE"});
//        while (mrs.next()){
//            log.info("表：{}", mrs.getString(3));
//        }



        conn.close();


    }

    /**
     * 查看数据库中的表、表字段、字段类型以及大小
     */
    @Test
    void reviewAll() {

        String url = "jdbc:mysql://localhost:3306/ciallo";
        String username = "root";
        String password = "Hd15364514749+";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData metaData = conn.getMetaData();

            // 获取数据库中的所有表
            ResultSet tables = metaData.getTables(null, null, "%", new String[] { "TABLE" });
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // 获取表的列信息
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String dataType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    String nullable = columns.getInt("NULLABLE") == DatabaseMetaData.columnNullable ? "YES" : "NO";

                    System.out.println("    Column Name: " + columnName);
                    System.out.println("    Data Type: " + dataType);
                    System.out.println("    Column Size: " + columnSize);
                    System.out.println("    Nullable: " + nullable);
                    System.out.println();
                }
                columns.close();
            }
            tables.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
