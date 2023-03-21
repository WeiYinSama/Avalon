package net.avalon.goodsdemo.dao;


import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;
import net.avalon.goodsdemo.mapper.generator.ProductPoMapper;
import net.avalon.goodsdemo.mapper.generator.po.OnSalePo;
import net.avalon.goodsdemo.mapper.generator.po.ProductPo;
import net.avalon.goodsdemo.mapper.generator.po.ProductPoExample;
import net.avalon.goodsdemo.util.AvalonException;
import net.avalon.goodsdemo.util.AvalonStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/21 - 23:29
 */
@Slf4j
@Repository
public class ProductDao {

    @Value("${goodsdemo.product.max-related-product}")
    private int max_related_product;

    @Autowired
    private ProductPoMapper productPoMapper;

    @Autowired
    private OnSaleDao onSaleDao;

    public Product createProduct(Product product, User user) throws AvalonException {

        Product ret = null;
        ProductPo po = product.createPo();
        po.setGmtCreate(LocalDateTime.now());
        po.setCreatorId(user.getId());
        po.setCreatorName(user.getName());
        try {
            productPoMapper.insertSelective(po);
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
        ret = new Product(po);
        return ret;
    }


    public Product getProductById(Long productId, boolean all) throws AvalonException {
        Product ret = null;
        try {
            ProductPo po = productPoMapper.selectByPrimaryKey(productId);
            if (po == null) {
                throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST, "商品 id 不存在");
            }
            ret = all ? retrieveFullProduct(po) : new Product(po);
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
        return ret;
    }


    private Product retrieveFullProduct(ProductPo productPo) throws DataAccessException {

        Product product = new Product(productPo);

        List<OnSalePo> latestOnSalePo = onSaleDao.getLatestOnSale(productPo.getId());
        product.addOnSale(latestOnSalePo);

        List<ProductPo> otherProductPo = retrieveOtherProduct(productPo);
        product.addOtherProduct(otherProductPo);
        return product;
    }


    private List<ProductPo> retrieveOtherProduct(ProductPo productPo) throws DataAccessException {

        ProductPoExample example = new ProductPoExample();
        example.createCriteria()
                .andGoodsIdEqualTo(productPo.getGoodsId())
                .andIdNotEqualTo(productPo.getId());
        PageHelper.startPage(1, max_related_product);

        return productPoMapper.selectByExample(example);
    }


    public List<Product> getProductByName(String name, boolean all, int page, int pageSize) throws AvalonException {
        List<Product> ret = null;
        ProductPoExample example = new ProductPoExample();
        example.createCriteria()
                .andNameEqualTo(name);
        List<ProductPo> list = null;
        PageHelper.startPage(page, pageSize, false);
        try {
            list = productPoMapper.selectByExample(example);
            if (list.isEmpty()) {
                throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST, "商品名称不存在");
            }
            ret = new ArrayList<>(list.size());
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
        for (ProductPo p : list) {
            Product product = all ? retrieveFullProduct(p) : new Product(p);
            ret.add(product);
        }
        return ret;
    }


    public void modiProduct(Product product, User user) throws AvalonException {
        ProductPo po = product.createPo();
        po.setGmtModified(LocalDateTime.now());
        po.setModifierId(user.getId());
        po.setModifierName(user.getName());
        try {
            int ret = productPoMapper.updateByPrimaryKeySelective(po);
            if (ret == 0) {
                throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST, "商品id不存在");
            }
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
    }


    public void deleteProduct(Long productId) throws AvalonException {
        try {
            int ret = productPoMapper.deleteByPrimaryKey(productId);
            if (ret == 0) {
                throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST, "商品id不存在");
            }
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
    }


}



