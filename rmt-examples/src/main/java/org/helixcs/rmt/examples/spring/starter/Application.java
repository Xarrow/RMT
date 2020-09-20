package org.helixcs.rmt.examples.spring.starter;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Application {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
    
}
