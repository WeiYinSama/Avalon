package net.avalon.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.avalon.mybatis.mapper.generator.ProductPoMapper;
import net.avalon.mybatis.mapper.generator.po.ProductPo;
import net.avalon.mybatis.mapper.generator.po.ProductPoExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisApplicationTests {

    @Autowired
    private ProductPoMapper mapper;


    /**
     * 条件查询
     */
    @Test
    void testSelectByExample() {

        ProductPoExample example = new ProductPoExample();
        example.createCriteria().andNameLike("%汤%");
        List<ProductPo> list = mapper.selectByExample(example);
        list.forEach(System.out::println);
    }

    /**
     * 根据主键选择性更新
     */
    @Test
    void testUpdateByPrimaryKeySelective() {
        var productPo = new ProductPo();
        productPo.setId(1550L);
        productPo.setName("xixi");

        mapper.updateByPrimaryKeySelective(productPo);
    }

    /**
     * 分页
     * 查询之前开启分页功能
     * <p>
     * limit index,pageSize
     * index: 从0开始
     * pageNum: 从1开始
     * index = (pageNum-1)*pageSize
     */
    @Test
    void testPageHelper() {
        PageHelper.startPage(1, 10, false);
        List<ProductPo> list = mapper.selectByExample(null);
        list.forEach(System.out::println);
    }

    /**
     * 分页查询 - Page
     */
    @Test
    void testPage() {
        Page<ProductPo> page = PageHelper.startPage(1, 10);
        List<ProductPo> list = mapper.selectByExample(null);
        System.out.println(page);
    }

    /**
     * 分页查询 - PageInfo
     *
     * prePage=0, nextPage=2,
     * isFirstPage=true, isLastPage=false,
     * hasPreviousPage=false, hasNextPage=true,
     * navigatePages=5,
     * navigateFirstPage=1, navigateLastPage=5,
     * navigatepageNums=[1, 2, 3, 4, 5]
     */
    @Test
    void testPageInfo() {
        PageHelper.startPage(1, 10);
        List<ProductPo> list = mapper.selectByExample(null);
        PageInfo<ProductPo> pageInfo = new PageInfo<>(list, 5);
        System.out.println(pageInfo);
    }

}
