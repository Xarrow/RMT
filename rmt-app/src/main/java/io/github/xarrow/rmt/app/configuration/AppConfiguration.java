package io.github.xarrow.rmt.app.configuration;

import io.github.xarrow.rmt.api.annotation.EnableRMT;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@SpringBootApplication(scanBasePackages = {"io.github.xarrow.rmt"})
@EnableRMT
public class AppConfiguration implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println(":)");
    }
}
