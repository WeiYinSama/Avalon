package net.avalon.goodsdemo.dao;

import lombok.extern.slf4j.Slf4j;
import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 15:05
 */
@SpringBootTest
@Slf4j
class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    void getProductById() {
        var product = productDao.getProductById(1557L, true);
        log.error("error");
        log.warn("warn");
        log.info("info: {}", product);
        log.debug("debug");
        log.trace("trace");
    }

    @Test
    void getProductByName() {
        var list = productDao.getProductByName("%桃%",false,1,10);
        list.forEach(System.out::println);
    }

    @Test
    void modiProduct() {
        Product product = new Product();
        product.setId(1557L);
        product.setName("美味奥利奥");
        User user = new User(472L,"琪亚娜");

        productDao.modiProduct(product,user);
    }

    @Test
    void deleteProduct() {
        productDao.deleteProduct(1551L);
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setName("夹心酱");
        User user = new User(472L,"琪亚娜");

        Product ret = productDao.createProduct(product, user);
        System.out.println(ret);
    }
}