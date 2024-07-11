package net.avalon.authority.dao;

import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.dao.bo.InviteCode;
import net.avalon.authority.mapper.generator.InviteCodePoMapper;
import net.avalon.authority.mapper.generator.po.InviteCodePo;
import net.avalon.authority.mapper.generator.po.InviteCodePoExample;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2024/1/31 - 18:52
 */
@Repository
@Slf4j
public class InviteCodeDao {

    @Autowired
    private InviteCodePoMapper mapper;


    /**
     * 生成邀请码
     * @return
     */
    public InviteCode generateCode() {

        InviteCodePo po = new InviteCodePo();
        String code = Common.genSeqNum(1);
        po.setCode(code);
        po.setCreateBy(1L);
        po.setAvailableCount((byte) 1);
        po.setCreateTime(LocalDateTime.now());

        try {
            mapper.insertSelective(po);
        } catch (DataAccessException e) {
            if (e.getMessage().contains("invite_code.code_unique_index")) {
                throw new AvalonException(AvalonStatus.INSERT_FAIL, "邀请码重复，请重试");
            } else {
                throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
            }
        }
        return new InviteCode(po);
    }

    public Boolean checkCodeAvailable(String code) {

        InviteCodePo po = new InviteCodePo();
        po.setCode(code);

        InviteCodePoExample example = new InviteCodePoExample();
        example.or().andCodeEqualTo(code);
        List<InviteCodePo> pos = mapper.selectByExample(example);
        if (pos.size() == 0) {
            throw new AvalonException(AvalonStatus.FIELD_NOTVALID, "无效邀请码");
        }
        return pos.get(0).getAvailableCount() > 0;
    }
}
