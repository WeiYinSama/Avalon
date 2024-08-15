package net.avalon.dynamicsql.mapper.generator;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ComicPoDynamicSqlSupport {
    public static final ComicPo comicPo = new ComicPo();

    public static final SqlColumn<Long> id = comicPo.id;

    public static final SqlColumn<String> name = comicPo.name;

    public static final SqlColumn<String> cover = comicPo.cover;

    public static final SqlColumn<String> author = comicPo.author;

    public static final SqlColumn<String> nameCn = comicPo.nameCn;

    public static final SqlColumn<String> translator = comicPo.translator;

    public static final SqlColumn<String> introduction = comicPo.introduction;

    public static final SqlColumn<Long> view = comicPo.view;

    public static final SqlColumn<String> region = comicPo.region;

    public static final SqlColumn<Byte> adult = comicPo.adult;

    public static final SqlColumn<Byte> type = comicPo.type;

    public static final SqlColumn<Long> categoryId = comicPo.categoryId;

    public static final SqlColumn<Long> userId = comicPo.userId;

    public static final SqlColumn<Byte> deleted = comicPo.deleted;

    public static final SqlColumn<LocalDateTime> createTime = comicPo.createTime;

    public static final SqlColumn<LocalDateTime> updateTime = comicPo.updateTime;

    public static final class ComicPo extends AliasableSqlTable<ComicPo> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> cover = column("cover", JDBCType.VARCHAR);

        public final SqlColumn<String> author = column("author", JDBCType.VARCHAR);

        public final SqlColumn<String> nameCn = column("name_cn", JDBCType.VARCHAR);

        public final SqlColumn<String> translator = column("translator", JDBCType.VARCHAR);

        public final SqlColumn<String> introduction = column("introduction", JDBCType.VARCHAR);

        public final SqlColumn<Long> view = column("view", JDBCType.BIGINT);

        public final SqlColumn<String> region = column("region", JDBCType.VARCHAR);

        public final SqlColumn<Byte> adult = column("adult", JDBCType.TINYINT);

        public final SqlColumn<Byte> type = column("type", JDBCType.TINYINT);

        public final SqlColumn<Long> categoryId = column("category_id", JDBCType.BIGINT);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Byte> deleted = column("deleted", JDBCType.TINYINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public ComicPo() {
            super("comic", ComicPo::new);
        }
    }
}