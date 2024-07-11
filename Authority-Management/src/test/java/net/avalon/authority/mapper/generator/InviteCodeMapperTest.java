package net.avalon.authority.mapper.generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2024/2/5 - 18:08
 */
@SpringBootTest
class InviteCodeMapperTest {

    @Autowired
    private InviteCodeMapper mapper;

    @Test
    void useInviteCodeByCode() {

        String code = "202401311913156P4A";
        int i = mapper.useInviteCodeByCode(code);
        System.out.println("i = " + i);
    }
}