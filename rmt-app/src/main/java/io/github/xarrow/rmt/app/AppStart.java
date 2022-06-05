package io.github.xarrow.rmt.app;

import io.github.xarrow.rmt.app.configuration.AppConfiguration;
import io.github.xarrow.rmt.expand.ExpandConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
public class AppStart {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder()
            .sources(AppConfiguration.class, ExpandConfiguration.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}
