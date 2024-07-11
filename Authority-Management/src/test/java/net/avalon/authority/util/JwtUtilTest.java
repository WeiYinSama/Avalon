package net.avalon.authority.util;

import net.avalon.generic.core.util.Common;
import net.avalon.generic.core.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @Author: Weiyin
 * @Create: 2024/1/22 - 20:01
 */
@SpringBootTest
public class JwtUtilTest {

    private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLouqvku73moKHpqowiLCJhdWQiOiJBdmFsb24iLCJ1aWQiOjEsImlzcyI6IkxhY2lhIiwibmFtZSI6IkhlRCIsImV4cCI6MTcwNTkyODgyMiwiaWF0IjoxNzA1OTI1MjIyfQ.Ad8pNuSw1fPk9syiyCGTzQYnr4Q05BwdQKylL2ZFX74";

    @Test
    void initToken() {

        JwtUtil.User user = new JwtUtil.User();
        user.setId(1L);
        user.setName("HeD");

        String token = JwtUtil.createToken(user, 3600);
        System.out.println(token);
    }

    @Test
    void verify() {
        JwtUtil.TokenInfo info = JwtUtil.verifyToken(token);
        System.out.println(info);
    }

    @Test
    void name() {
        for (int i = 0; i < 10; i++) {
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(s.substring(0,16).toUpperCase());
        }

    }
}
