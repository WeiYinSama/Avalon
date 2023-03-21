package net.avalon.goodsdemo.service;

import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/23 - 1:27
 */
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct() {
        Product product = new Product();
        product.setName("肯德基");
        product.setOriginalPrice(50L);

        User user = new User(427L,"琪亚娜");
        Product ret = productService.createProduct(product, user);
        System.out.println(ret);
    }

    @Test
    void getProductById() {

        Product product = productService.getProductById(1557L, true);
        System.out.println(product);
    }

    @Test
    void getProductByName() {
        List<Product> list = productService.getProductByName("美味奥利奥", true,1,10);
        list.forEach(System.out::println);
    }

    @Test
    void modifyProduct() {
        Product product = new Product();
        product.setId(1557L);
        product.setName("扭一扭~");
        productService.modifyProduct(product,new User(724L,"琪亚娜"));
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(5496L);
    }
}