package net.avalon.dynamicsql.controller;


import com.github.pagehelper.PageHelper;
import net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport;
import net.avalon.dynamicsql.mapper.generator.ComicPoMapper;
import net.avalon.dynamicsql.mapper.generator.po.ComicPo;
import org.mybatis.dynamic.sql.ExistsPredicate;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport.*;


/**
 * @Author: Heda
 * @Create: 2024/8/15
 */
@RestController
public class ComicController {

    @Autowired
    private ComicPoMapper mapper;


    @GetMapping("/page")
    public List<ComicPo> page(@RequestParam(required = false) String name, @RequestParam(required = false) String author) {

        var table = select(comicPo.allColumns()).from(comicPo);
        var conditions = table.where(deleted,isEqualTo((byte) 0));

        if (name!= null) {
            conditions.and(comicPo.name, isLike("%" + name + "%"));
        }
        if(author != null){
            conditions.and(comicPo.author, isLike("%" + author + "%"));
        }
        SelectStatementProvider provider = conditions.orderBy(comicPo.id.descending())
                .build().render(RenderingStrategies.MYBATIS3);

        PageHelper.startPage(1,10,false);
        return mapper.selectMany(provider);
    }
}
