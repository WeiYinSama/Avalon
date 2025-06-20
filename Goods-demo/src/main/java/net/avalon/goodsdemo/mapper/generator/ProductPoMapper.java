package net.avalon.goodsdemo.mapper.generator;

import java.util.List;
import net.avalon.goodsdemo.mapper.generator.po.ProductPo;
import net.avalon.goodsdemo.mapper.generator.po.ProductPoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ProductPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int insert(ProductPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int insertSelective(ProductPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    List<ProductPo> selectByExample(ProductPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    ProductPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") ProductPo row, @Param("example") ProductPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") ProductPo row, @Param("example") ProductPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oomall_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductPo row);
}