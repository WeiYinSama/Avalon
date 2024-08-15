package net.avalon.dynamicsql.mapper;

import net.avalon.dynamicsql.Comic;
import net.avalon.dynamicsql.mapper.generator.ComicPoMapper;
import net.avalon.dynamicsql.mapper.generator.po.ComicPo;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport.*;
import static net.avalon.dynamicsql.mapper.generator.LabelPoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * @Author: Heda
 * @Create: 2024/7/26
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private ComicPoMapper comicPoMapper;


    @Test
    void s1() {

        SelectStatementProvider provider = select(comicPo.name, author)
                .from(comicPo)
                .where(comicPo.id,isEqualTo(9L))
                .build().render(RenderingStrategies.MYBATIS3);
//        PageHelper.startPage(2,10,false);
        List<ComicPo> pos = comicPoMapper.selectMany(provider);

        pos.forEach(System.out::println);
    }

    @Test
    void s2() {

        SelectStatementProvider provider = select(comicPo.name, labelPo.name.as("label"))
                .from(comicPo).join(labelPo)
                .on(comicPo.id, equalTo(labelPo.comicId))
                .where(comicPo.id, isEqualTo(9L))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Comic> comics = comicPoMapper.twoTable(provider);
        comics.forEach(System.out::println);
    }
}
