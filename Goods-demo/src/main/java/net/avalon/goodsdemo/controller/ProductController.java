package net.avalon.goodsdemo.controller;

import net.avalon.goodsdemo.controller.vo.ProductRetVo;
import net.avalon.goodsdemo.dao.bo.Product;
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
 * @Create: 2023/2/22 - 23:49
 */
@RestController
@RequestMapping(value = "/customer/products")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping("{id}")
    public ResponseBody getProductById(@PathVariable("id") Long id) {

        ResponseBody ret = null;

        Product product = productService.getProductById(id, true);
        ProductRetVo productRetVo = new ProductRetVo(product);
        ret = ResponseUtil.ok(productRetVo);

        return ret;
    }

    @GetMapping("")
    public ResponseBody getProductByName(@RequestParam String name,
                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        ResponseBody ret = null;

        List<Product> list = productService.getProductByName(name, true, page, pageSize);

        List<ProductRetVo> data = new ArrayList<>(list.size());
        list.forEach(e -> data.add(new ProductRetVo(e)));

        ret = ResponseUtil.ok(data);

        return ret;
    }


}
