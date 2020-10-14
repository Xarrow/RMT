package io.github.xarrow.rmt.example.spring.booter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 9/25/2020.
 * @Desc:
 */
@SpringBootApplication(scanBasePackages = "io.github.xarrow.rmt.example.spring.booter")
@Configuration
@Import(ExpandListenerInitialization.class)
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(Application.class).run(args);

    }
}
