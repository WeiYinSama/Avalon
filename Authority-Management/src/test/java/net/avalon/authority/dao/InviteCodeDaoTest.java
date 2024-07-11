package net.avalon.authority.dao;

import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.dao.bo.InviteCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2024/1/31 - 18:58
 */
@SpringBootTest
@Slf4j
class InviteCodeDaoTest {

    @Autowired
    private InviteCodeDao dao;

    @Test
    void generateCode() {
        for (int i = 0; i <10 ; i++) {
            InviteCode code = dao.generateCode();
            log.debug("邀请码：{}",code);
        }


    }
}