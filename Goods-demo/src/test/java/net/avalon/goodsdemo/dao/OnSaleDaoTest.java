package net.avalon.goodsdemo.dao;

import net.avalon.goodsdemo.mapper.generator.po.OnSalePo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 17:49
 */
@SpringBootTest
class OnSaleDaoTest {

    @Autowired
    private OnSaleDao onSaleDao;

    @Test
    void getLatestOnSale() {
        List<OnSalePo> list = onSaleDao.getLatestOnSale(1557L);
        System.out.println(list.size());
        list.forEach(System.out::println);
    }
}