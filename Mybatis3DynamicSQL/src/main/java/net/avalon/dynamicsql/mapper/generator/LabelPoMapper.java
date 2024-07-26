package net.avalon.dynamicsql.mapper.generator;

import static net.avalon.dynamicsql.mapper.generator.LabelPoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import net.avalon.dynamicsql.mapper.generator.po.LabelPo;
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
public interface LabelPoMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    BasicColumn[] selectList = BasicColumn.columnList(id, name, comicId, userId, deleted, createTime, updateTime);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<LabelPo> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="LabelPoResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="comic_id", property="comicId", jdbcType=JdbcType.BIGINT),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.TINYINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<LabelPo> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("LabelPoResult")
    Optional<LabelPo> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source Table: label")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default int insert(LabelPo row) {
        return MyBatis3Utils.insert(this::insert, row, labelPo, c ->
            c.map(name).toProperty("name")
            .map(comicId).toProperty("comicId")
            .map(userId).toProperty("userId")
            .map(deleted).toProperty("deleted")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default int insertSelective(LabelPo row) {
        return MyBatis3Utils.insert(this::insert, row, labelPo, c ->
            c.map(name).toPropertyWhenPresent("name", row::getName)
            .map(comicId).toPropertyWhenPresent("comicId", row::getComicId)
            .map(userId).toPropertyWhenPresent("userId", row::getUserId)
            .map(deleted).toPropertyWhenPresent("deleted", row::getDeleted)
            .map(createTime).toPropertyWhenPresent("createTime", row::getCreateTime)
            .map(updateTime).toPropertyWhenPresent("updateTime", row::getUpdateTime)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default Optional<LabelPo> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default List<LabelPo> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default List<LabelPo> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default Optional<LabelPo> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, labelPo, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    static UpdateDSL<UpdateModel> updateAllColumns(LabelPo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalTo(row::getName)
                .set(comicId).equalTo(row::getComicId)
                .set(userId).equalTo(row::getUserId)
                .set(deleted).equalTo(row::getDeleted)
                .set(createTime).equalTo(row::getCreateTime)
                .set(updateTime).equalTo(row::getUpdateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(LabelPo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalToWhenPresent(row::getName)
                .set(comicId).equalToWhenPresent(row::getComicId)
                .set(userId).equalToWhenPresent(row::getUserId)
                .set(deleted).equalToWhenPresent(row::getDeleted)
                .set(createTime).equalToWhenPresent(row::getCreateTime)
                .set(updateTime).equalToWhenPresent(row::getUpdateTime);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default int updateByPrimaryKey(LabelPo row) {
        return update(c ->
            c.set(name).equalTo(row::getName)
            .set(comicId).equalTo(row::getComicId)
            .set(userId).equalTo(row::getUserId)
            .set(deleted).equalTo(row::getDeleted)
            .set(createTime).equalTo(row::getCreateTime)
            .set(updateTime).equalTo(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1324677+08:00", comments="Source Table: label")
    default int updateByPrimaryKeySelective(LabelPo row) {
        return update(c ->
            c.set(name).equalToWhenPresent(row::getName)
            .set(comicId).equalToWhenPresent(row::getComicId)
            .set(userId).equalToWhenPresent(row::getUserId)
            .set(deleted).equalToWhenPresent(row::getDeleted)
            .set(createTime).equalToWhenPresent(row::getCreateTime)
            .set(updateTime).equalToWhenPresent(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }
}