package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.spring.boot.starter.autoconfigure.RmtImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static io.github.xarrow.rmt.commons.RmtCommons.ENABLE;
import static io.github.xarrow.rmt.commons.RmtCommons.RMT_STARTER_ENABLE_PREFIX;

@Slf4j
@Configuration
@EnableConfigurationProperties(RmtStarterProperties.class)
@ConditionalOnClass({RequestMappingHandlerMapping.class, Environment.class})
@ConditionalOnProperty(prefix = RMT_STARTER_ENABLE_PREFIX, name = ENABLE, havingValue = "true")
@Import({RmtImportSelector.class})
public class RmtStarterAutoConfiguration implements CommandLineRunner {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private RmtStarterProperties rmtStarterProperties;
    @Autowired
    private Environment environment;


    @Override
    public void run(String... args) {
        String port = context.getEnvironment().getProperty("local.server.port");
        String env = environment.getProperty("env");
        String webPath = rmtStarterProperties.getWebPath();
        log.info("\n========================>\nRMT started at:: http://127.0.0.1:{}/{}\nCurrent env is::{}",
                port,
                webPath,
                env);
    }

}
