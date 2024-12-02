package net.avalon.nacos;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NacosDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(NacosDemoApplication.class, args);
    }

}
