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
@Import(ExpandListenerInitialization.class)
@Slf4j
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(Application.class).run(args);
        String property = context.getEnvironment().getProperty("local.server.port");
        System.out.println(property);
    }

}
