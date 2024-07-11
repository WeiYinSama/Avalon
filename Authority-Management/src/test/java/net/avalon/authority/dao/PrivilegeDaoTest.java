package net.avalon.authority.dao;

import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.dao.bo.Privilege;
import net.avalon.authority.mapper.generator.PrivilegePoMapper;
import net.avalon.authority.mapper.generator.po.PrivilegePo;
import net.avalon.authority.mapper.generator.po.PrivilegePoExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2024/1/28 - 10:54
 */
@SpringBootTest
@Slf4j
class PrivilegeDaoTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PrivilegePoMapper mapper;
    @Autowired
    private PrivilegeDao dao;

    @Test
    void loadPrivInfo() {
        PrivilegePoExample example = new PrivilegePoExample();
        example.or().andDeletedEqualTo((byte) 1);
        List<PrivilegePo> privilegePos = mapper.selectByExample(example);
        for (PrivilegePo po : privilegePos) {
            Privilege priv = new Privilege(po);
            String key = String.format("%s-%s",po.getUrl(),po.getRequestType());
            log.debug("Key：{}\tValue：{}",key,po.getId());
        }
    }

    @Test
    void getPrivIdByUrl() {
        String priv = redisTemplate.opsForHash().get("Priv", "/priv/add-POST").toString();
        int i = Integer.parseInt(priv);
        System.out.println(i);
    }

    @Test
    void findAll() {
        List<Privilege> all = dao.findAll();
        all.forEach(System.out::println);


    }
}