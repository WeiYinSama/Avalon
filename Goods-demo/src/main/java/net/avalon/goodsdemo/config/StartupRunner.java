//School of Informatics Xiamen University, GPL-3.0 license
package net.avalon.goodsdemo.config;


import lombok.extern.slf4j.Slf4j;
import net.avalon.goodsdemo.dao.bo.User;
import net.avalon.goodsdemo.util.JwtUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动后执行 生成管理员 token
 *
 * @author Lacia
 */
@Component
@Slf4j
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        String adminToken = JwtUtil.createToken(new User(427L, "琪亚娜"), 5 * 60 * 60);
        log.info("test token = {}", adminToken);
    }
}
