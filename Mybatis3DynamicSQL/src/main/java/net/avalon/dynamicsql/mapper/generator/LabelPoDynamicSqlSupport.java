package net.avalon.dynamicsql.mapper.generator;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class LabelPoDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    public static final LabelPo labelPo = new LabelPo();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.id")
    public static final SqlColumn<Long> id = labelPo.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.name")
    public static final SqlColumn<String> name = labelPo.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.comic_id")
    public static final SqlColumn<Long> comicId = labelPo.comicId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.user_id")
    public static final SqlColumn<Long> userId = labelPo.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.deleted")
    public static final SqlColumn<Byte> deleted = labelPo.deleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.create_time")
    public static final SqlColumn<LocalDateTime> createTime = labelPo.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.update_time")
    public static final SqlColumn<LocalDateTime> updateTime = labelPo.updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    public static final class LabelPo extends AliasableSqlTable<LabelPo> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<Long> comicId = column("comic_id", JDBCType.BIGINT);

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT);

        public final SqlColumn<Byte> deleted = column("deleted", JDBCType.TINYINT);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public LabelPo() {
            super("label", LabelPo::new);
        }
    }
}