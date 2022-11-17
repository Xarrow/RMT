package io.github.xarrow.rmt.app;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class RmtApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(RmtConfiguration.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
