package io.github.xarrow.rmt.example.spring.booter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Email: zhangjian12424@gmail.com .
 * @Author: helixcs
 * @Date: 9/25/2020.
 * @Desc:
 */
@SpringBootApplication(scanBasePackages = "io.github.xarrow.rmt.example.spring.booter")
@Configuration
@Import(ExpandListenerInitialization.class)
@Slf4j
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(Application.class).run(args);
        String property = context.getEnvironment().getProperty("local.server.port");
        System.out.println(property);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("RMT start at http://127.0.0.1:8080/rmt");
    }
}
