package org.helixcs.rmt.app.configuration;

import org.helixcs.rmt.api.annotation.EnableRMT;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@SpringBootApplication(scanBasePackages = {"org.helixcs.rmt"})
@EnableRMT
public class AppConfiguration implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println(":)");
    }
}
