package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.commons.TerminalUtils;
import io.github.xarrow.rmt.spring.boot.starter.autoconfigure.RmtImportSelector;
import io.github.xarrow.rmt.spring.boot.starter.autoconfigure.RmtStaticFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Configuration
@EnableConfigurationProperties(RmtStarterProperties.class)
@ConditionalOnClass({RequestMappingHandlerMapping.class, Environment.class})
@ConditionalOnProperty(name = "rmt.starter.enabled", matchIfMissing = false)
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

        String pathPattern = TerminalUtils.deduceStaticWebPattern(rmtStarterProperties.getStaticWebPath());
        String staticFileLocation = TerminalUtils.deduceStaticLocationPath(rmtStarterProperties.getStaticFileLocation());
        String staticFileServiceViewPath = TerminalUtils.webPathFilter(rmtStarterProperties.getStaticFileServiceViewPath());


        String rmtTips = String.format("\n========================>" +
                        "\nRMT current env is::%s" +
                        "\nRMT started at:: http://127.0.0.1:%s/%s" +
                        "\nStatic file path at:: http://127.0.0.1:%s%s" +
                        "\nStatic file view at:: http://127.0.0.1:%s%s" +
                        "\nStatic file mapping:: local[%s] ==> web[%s]",
                env, port, webPath,
                port, pathPattern,
                port, staticFileServiceViewPath,
                staticFileLocation, pathPattern);

        log.info(rmtTips);
    }

    @Bean
    public RmtStaticFileService rmtStaticFileService() {
        return new RmtStaticFileService();
    }

    @Bean
    @Primary
    public WebMvcProperties webMvcProperties() {
        WebMvcProperties webMvcProperties = new WebMvcProperties();
        webMvcProperties.getView().setPrefix("/");
        webMvcProperties.getView().setSuffix(".html");
        return webMvcProperties;
    }
}
