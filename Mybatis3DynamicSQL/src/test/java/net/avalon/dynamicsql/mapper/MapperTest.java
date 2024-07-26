package net.avalon.dynamicsql.mapper;

import net.avalon.dynamicsql.Comic;
import net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport;
import net.avalon.dynamicsql.mapper.generator.ComicPoMapper;
import net.avalon.dynamicsql.mapper.generator.LabelPoDynamicSqlSupport;
import net.avalon.dynamicsql.mapper.generator.LabelPoMapper;
import net.avalon.dynamicsql.mapper.generator.po.ComicPo;
import net.avalon.dynamicsql.mapper.generator.po.LabelPo;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport.author;
import static org.mybatis.dynamic.sql.SqlBuilder.*;
import net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Heda
 * @Create: 2024/7/26
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private ComicPoMapper comicPoMapper;

    @Autowired
    private LabelPoMapper labelPoMapper;

    @Test
    void s1() {

        SelectStatementProvider provider = select(ComicPoDynamicSqlSupport.name, author)
                .from(ComicPoDynamicSqlSupport.comicPo)
                .limit(10)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<ComicPo> pos = comicPoMapper.selectMany(provider);
        pos.forEach(System.out::println);
    }

    @Test
    void twoTable() {
        SelectStatementProvider provider = select(ComicPoDynamicSqlSupport.name.as("name"), LabelPoDynamicSqlSupport.name.as("label"))
                .from(ComicPoDynamicSqlSupport.comicPo).leftJoin(LabelPoDynamicSqlSupport.labelPo)
                .on(ComicPoDynamicSqlSupport.id, equalTo(LabelPoDynamicSqlSupport.comicId))
                .where(ComicPoDynamicSqlSupport.id, isEqualTo(23L))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<Comic> pos = comicPoMapper.twoTable(provider);
        pos.forEach(System.out::println);
    }

    @Test
    void insertTest() {
        LabelPo po = new LabelPo();
        po.setName("美少女");
        po.setComicId(23L);
        po.setDeleted((byte) 0);

        int i = labelPoMapper.insertSelective(po);
        System.out.println("i = " + i);
    }
}
