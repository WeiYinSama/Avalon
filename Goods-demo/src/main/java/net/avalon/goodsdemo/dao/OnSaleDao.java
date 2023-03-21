package net.avalon.goodsdemo.dao;

import com.github.pagehelper.PageHelper;
import net.avalon.goodsdemo.mapper.generator.OnSalePoMapper;
import net.avalon.goodsdemo.mapper.generator.po.OnSalePo;
import net.avalon.goodsdemo.mapper.generator.po.OnSalePoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/22 - 17:11
 */
@Repository
public class OnSaleDao {

    @Autowired
    private OnSalePoMapper onSalePoMapper;

    /**
     * 获得商品最近的上架信息
     * @param productId
     * @return
     * @throws DataAccessException
     */
    public List<OnSalePo> getLatestOnSale(Long productId) throws DataAccessException {
        List<OnSalePo> ret = null;
        OnSalePoExample example = new OnSalePoExample();
        example.createCriteria()
                .andProductIdEqualTo(productId)
                .andBeginTimeLessThanOrEqualTo(LocalDateTime.now());
        example.setOrderByClause("end_time DESC");
        PageHelper.startPage(1, 1);
        ret = onSalePoMapper.selectByExample(example);

        return ret;
    }
}
