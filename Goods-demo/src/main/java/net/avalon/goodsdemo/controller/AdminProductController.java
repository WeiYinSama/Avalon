package net.avalon.goodsdemo.controller;

import jakarta.validation.Valid;
import net.avalon.goodsdemo.controller.vo.ProductRetVo;
import net.avalon.goodsdemo.controller.vo.ProductVo;
import net.avalon.goodsdemo.core.aop.annotation.Audit;
import net.avalon.goodsdemo.core.aop.annotation.LoginName;
import net.avalon.goodsdemo.core.aop.annotation.LoginUser;
import net.avalon.goodsdemo.dao.bo.Product;
import net.avalon.goodsdemo.dao.bo.User;
import net.avalon.goodsdemo.service.ProductService;

import net.avalon.goodsdemo.util.ResponseBody;
import net.avalon.goodsdemo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 23:50
 */
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ResponseBody getProductById(@PathVariable("id") Long id) {
        ResponseBody ret = null;
        Product product = null;

        product = productService.getProductById(id, false);
        ProductRetVo productRetVo = new ProductRetVo(product);

        ret = ResponseUtil.ok(productRetVo);

        return ret;
    }


    @GetMapping("")
    public ResponseBody searchProductByName(@RequestParam String name,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        System.out.println(page);
        System.out.println(pageSize);

        ResponseBody ret = null;

        List<Product> productList = null;
        productList = productService.getProductByName(name, false, page, pageSize);
        if (null != productList) {
            List<ProductRetVo> data = new ArrayList<>(productList.size());
            for (Product bo : productList) {
                data.add(new ProductRetVo(bo));
            }
            ret = ResponseUtil.ok(data);
        } else {
            ret = ResponseUtil.ok();
        }

        return ret;
    }

    @PostMapping("")
    @Audit
    public ResponseBody createProduct(@Valid @RequestBody ProductVo productVo,@LoginUser Long userId, @LoginName String userName) {
        ResponseBody ret = null;

        Product product = productVo.createBo();
        User user = new User(userId, userName);
        Product retProduct = productService.createProduct(product, user);

        ProductVo vo = new ProductVo(retProduct);

        ret = ResponseUtil.ok(vo);

        return ret;
    }

    @PutMapping("{id}")
    public ResponseBody modiProduct(@PathVariable Long id, @Valid @RequestBody ProductVo productVo) {
        System.out.println(id);
        ResponseBody ret = null;

        User user = new User(427L, "琪亚娜");

        Product product = productVo.createBo();
        product.setId(id);
        productService.modifyProduct(product, user);
        ret = ResponseUtil.ok();

        return ret;

    }

    @DeleteMapping("{id}")
    public ResponseBody delProduct(@PathVariable("id") Long id) {
        ResponseBody ret = null;

        productService.deleteProduct(id);
        ret = ResponseUtil.ok();

        return ret;
    }
}
