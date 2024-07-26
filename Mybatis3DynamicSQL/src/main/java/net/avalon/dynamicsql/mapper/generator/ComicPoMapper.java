package net.avalon.dynamicsql.mapper.generator;

import static net.avalon.dynamicsql.mapper.generator.ComicPoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import net.avalon.dynamicsql.Comic;
import net.avalon.dynamicsql.mapper.generator.po.ComicPo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface ComicPoMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1261484+08:00", comments="Source Table: comic")
    BasicColumn[] selectList = BasicColumn.columnList(id, name, cover, author, nameCn, translator, introduction, view, region, adult, type, categoryId, userId, deleted, createTime, updateTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1216385+08:00", comments="Source Table: comic")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<ComicPo> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1236178+08:00", comments="Source Table: comic")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ComicPoResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="cover", property="cover", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="name_cn", property="nameCn", jdbcType=JdbcType.VARCHAR),
        @Result(column="translator", property="translator", jdbcType=JdbcType.VARCHAR),
        @Result(column="introduction", property="introduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="view", property="view", jdbcType=JdbcType.BIGINT),
        @Result(column="region", property="region", jdbcType=JdbcType.VARCHAR),
        @Result(column="adult", property="adult", jdbcType=JdbcType.TINYINT),
        @Result(column="type", property="type", jdbcType=JdbcType.TINYINT),
        @Result(column="category_id", property="categoryId", jdbcType=JdbcType.BIGINT),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ComicPo> selectMany(SelectStatementProvider selectStatement);


    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="Comic", value = {
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="label", property="label", jdbcType=JdbcType.VARCHAR)
    })
    List<Comic> twoTable(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1246162+08:00", comments="Source Table: comic")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ComicPoResult")
    Optional<ComicPo> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1246162+08:00", comments="Source Table: comic")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1246162+08:00", comments="Source Table: comic")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1246162+08:00", comments="Source Table: comic")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1246162+08:00", comments="Source Table: comic")
    default int insert(ComicPo row) {
        return MyBatis3Utils.insert(this::insert, row, comicPo, c ->
            c.map(name).toProperty("name")
            .map(cover).toProperty("cover")
            .map(author).toProperty("author")
            .map(nameCn).toProperty("nameCn")
            .map(translator).toProperty("translator")
            .map(introduction).toProperty("introduction")
            .map(view).toProperty("view")
            .map(region).toProperty("region")
            .map(adult).toProperty("adult")
            .map(type).toProperty("type")
            .map(categoryId).toProperty("categoryId")
            .map(userId).toProperty("userId")
            .map(deleted).toProperty("deleted")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1261484+08:00", comments="Source Table: comic")
    default int insertSelective(ComicPo row) {
        return MyBatis3Utils.insert(this::insert, row, comicPo, c ->
            c.map(name).toPropertyWhenPresent("name", row::getName)
            .map(cover).toPropertyWhenPresent("cover", row::getCover)
            .map(author).toPropertyWhenPresent("author", row::getAuthor)
            .map(nameCn).toPropertyWhenPresent("nameCn", row::getNameCn)
            .map(translator).toPropertyWhenPresent("translator", row::getTranslator)
            .map(introduction).toPropertyWhenPresent("introduction", row::getIntroduction)
            .map(view).toPropertyWhenPresent("view", row::getView)
            .map(region).toPropertyWhenPresent("region", row::getRegion)
            .map(adult).toPropertyWhenPresent("adult", row::getAdult)
            .map(type).toPropertyWhenPresent("type", row::getType)
            .map(categoryId).toPropertyWhenPresent("categoryId", row::getCategoryId)
            .map(userId).toPropertyWhenPresent("userId", row::getUserId)
            .map(deleted).toPropertyWhenPresent("deleted", row::getDeleted)
            .map(createTime).toPropertyWhenPresent("createTime", row::getCreateTime)
            .map(updateTime).toPropertyWhenPresent("updateTime", row::getUpdateTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1261484+08:00", comments="Source Table: comic")
    default Optional<ComicPo> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1261484+08:00", comments="Source Table: comic")
    default List<ComicPo> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1276743+08:00", comments="Source Table: comic")
    default List<ComicPo> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1276743+08:00", comments="Source Table: comic")
    default Optional<ComicPo> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1276743+08:00", comments="Source Table: comic")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, comicPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1284537+08:00", comments="Source Table: comic")
    static UpdateDSL<UpdateModel> updateAllColumns(ComicPo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalTo(row::getName)
                .set(cover).equalTo(row::getCover)
                .set(author).equalTo(row::getAuthor)
                .set(nameCn).equalTo(row::getNameCn)
                .set(translator).equalTo(row::getTranslator)
                .set(introduction).equalTo(row::getIntroduction)
                .set(view).equalTo(row::getView)
                .set(region).equalTo(row::getRegion)
                .set(adult).equalTo(row::getAdult)
                .set(type).equalTo(row::getType)
                .set(categoryId).equalTo(row::getCategoryId)
                .set(userId).equalTo(row::getUserId)
                .set(deleted).equalTo(row::getDeleted)
                .set(createTime).equalTo(row::getCreateTime)
                .set(updateTime).equalTo(row::getUpdateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1284537+08:00", comments="Source Table: comic")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ComicPo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalToWhenPresent(row::getName)
                .set(cover).equalToWhenPresent(row::getCover)
                .set(author).equalToWhenPresent(row::getAuthor)
                .set(nameCn).equalToWhenPresent(row::getNameCn)
                .set(translator).equalToWhenPresent(row::getTranslator)
                .set(introduction).equalToWhenPresent(row::getIntroduction)
                .set(view).equalToWhenPresent(row::getView)
                .set(region).equalToWhenPresent(row::getRegion)
                .set(adult).equalToWhenPresent(row::getAdult)
                .set(type).equalToWhenPresent(row::getType)
                .set(categoryId).equalToWhenPresent(row::getCategoryId)
                .set(userId).equalToWhenPresent(row::getUserId)
                .set(deleted).equalToWhenPresent(row::getDeleted)
                .set(createTime).equalToWhenPresent(row::getCreateTime)
                .set(updateTime).equalToWhenPresent(row::getUpdateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1284537+08:00", comments="Source Table: comic")
    default int updateByPrimaryKey(ComicPo row) {
        return update(c ->
            c.set(name).equalTo(row::getName)
            .set(cover).equalTo(row::getCover)
            .set(author).equalTo(row::getAuthor)
            .set(nameCn).equalTo(row::getNameCn)
            .set(translator).equalTo(row::getTranslator)
            .set(introduction).equalTo(row::getIntroduction)
            .set(view).equalTo(row::getView)
            .set(region).equalTo(row::getRegion)
            .set(adult).equalTo(row::getAdult)
            .set(type).equalTo(row::getType)
            .set(categoryId).equalTo(row::getCategoryId)
            .set(userId).equalTo(row::getUserId)
            .set(deleted).equalTo(row::getDeleted)
            .set(createTime).equalTo(row::getCreateTime)
            .set(updateTime).equalTo(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1294699+08:00", comments="Source Table: comic")
    default int updateByPrimaryKeySelective(ComicPo row) {
        return update(c ->
            c.set(name).equalToWhenPresent(row::getName)
            .set(cover).equalToWhenPresent(row::getCover)
            .set(author).equalToWhenPresent(row::getAuthor)
            .set(nameCn).equalToWhenPresent(row::getNameCn)
            .set(translator).equalToWhenPresent(row::getTranslator)
            .set(introduction).equalToWhenPresent(row::getIntroduction)
            .set(view).equalToWhenPresent(row::getView)
            .set(region).equalToWhenPresent(row::getRegion)
            .set(adult).equalToWhenPresent(row::getAdult)
            .set(type).equalToWhenPresent(row::getType)
            .set(categoryId).equalToWhenPresent(row::getCategoryId)
            .set(userId).equalToWhenPresent(row::getUserId)
            .set(deleted).equalToWhenPresent(row::getDeleted)
            .set(createTime).equalToWhenPresent(row::getCreateTime)
            .set(updateTime).equalToWhenPresent(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }
}