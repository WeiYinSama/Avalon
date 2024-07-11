package net.avalon.mybatis.mapper;

import net.avalon.mybatis.po.Comic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Weiyin
 * @Create: 2023/11/15 - 22:41
 */
@Mapper
public interface ComicMapper {

    void insertComic(Comic comic);

    void deleteById(Long id);

    void updateComic(Comic comic);

    @Select("select * from comic where id = #{id}")
    Comic getById(Long id);
}
