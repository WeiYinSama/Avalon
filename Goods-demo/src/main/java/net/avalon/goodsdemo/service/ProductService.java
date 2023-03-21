package net.avalon.goodsdemo.service;

import net.avalon.goodsdemo.dao.ProductDao;
import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;
import net.avalon.goodsdemo.util.AvalonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 23:34
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = AvalonException.class)
    public Product createProduct(Product product, User user) throws AvalonException {
        return productDao.createProduct(product, user);
    }

    public Product getProductById(Long id, boolean all) throws AvalonException {
        return productDao.getProductById(id, all);
    }

    public List<Product> getProductByName(String name, boolean all, int page, int pageSize) throws AvalonException {
        return productDao.getProductByName(name, all, page, pageSize);
    }

    @Transactional(rollbackFor = AvalonException.class)
    public void modifyProduct(Product product, User user) throws AvalonException {
        productDao.modiProduct(product, user);
    }

    @Transactional(rollbackFor = AvalonException.class)
    public void deleteProduct(Long id) throws AvalonException {
        productDao.deleteProduct(id);
    }
}
