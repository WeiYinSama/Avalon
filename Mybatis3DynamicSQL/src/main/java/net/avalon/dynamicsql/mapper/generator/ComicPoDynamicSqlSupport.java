package net.avalon.dynamicsql.mapper.generator;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ComicPoDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1200927+08:00", comments="Source Table: comic")
    public static final ComicPo comicPo = new ComicPo();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1200927+08:00", comments="Source field: comic.id")
    public static final SqlColumn<Long> id = comicPo.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.name")
    public static final SqlColumn<String> name = comicPo.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.cover")
    public static final SqlColumn<String> cover = comicPo.cover;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.author")
    public static final SqlColumn<String> author = comicPo.author;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.name_cn")
    public static final SqlColumn<String> nameCn = comicPo.nameCn;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.translator")
    public static final SqlColumn<String> translator = comicPo.translator;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.introduction")
    public static final SqlColumn<String> introduction = comicPo.introduction;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.view")
    public static final SqlColumn<Long> view = comicPo.view;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.region")
    public static final SqlColumn<String> region = comicPo.region;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.adult")
    public static final SqlColumn<Byte> adult = comicPo.adult;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.type")
    public static final SqlColumn<Byte> type = comicPo.type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.category_id")
    public static final SqlColumn<Long> categoryId = comicPo.categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.user_id")
    public static final SqlColumn<Long> userId = comicPo.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.deleted")
    public static final SqlColumn<Byte> deleted = comicPo.deleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.create_time")
    public static final SqlColumn<LocalDateTime> createTime = comicPo.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1205982+08:00", comments="Source field: comic.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = comicPo.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1200927+08:00", comments="Source Table: comic")
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