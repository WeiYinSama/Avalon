package net.avalon.rocketmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketmqDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
