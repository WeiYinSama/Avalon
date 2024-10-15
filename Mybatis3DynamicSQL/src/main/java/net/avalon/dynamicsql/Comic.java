package net.avalon.dynamicsql;

import lombok.Data;
import net.avalon.dynamicsql.mapper.generator.po.ComicPo;

/**
 * @Author: Heda
 * @Create: 2024/7/26
 */
@Data
public class Comic {

    private String name;
    private String label;

    private ComicPo comicPo;
}
