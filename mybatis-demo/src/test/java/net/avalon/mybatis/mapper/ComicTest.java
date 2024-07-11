package net.avalon.mybatis.mapper;

import net.avalon.mybatis.mapper.generator.ComicPoMapper;
import net.avalon.mybatis.mapper.generator.po.ComicPo;
import net.avalon.mybatis.po.Comic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2023/11/15 - 22:49
 */
@SpringBootTest
public class ComicTest {

    @Autowired
    private ComicMapper mapper;

    @Autowired
    private ComicPoMapper comicPoMapper;

    @Test
    void select1() {
        ComicPo po = comicPoMapper.selectByPrimaryKey(2L);
        System.out.println(po);
    }

    @Test
    public void test() {
        Comic comic = new Comic();
        comic.setName("绝世唐门");
        comic.setAuthor("唐家三少");
        comic.setDeleted((byte) 1);
        comic.setCreateTime(LocalDateTime.now());

        mapper.insertComic(comic);
        System.out.println(comic);
    }

    @Test
    public void delete() {
        mapper.deleteById(1L);
    }

    @Test
    public void update() {
        Comic comic = new Comic();
        comic.setName("斗罗大陆");
        comic.setId(2L);

        mapper.updateComic(comic);
    }

    @Test
    public void select() {
        Comic co = mapper.getById(2L);
        System.out.println(co);
    }
}
