package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.spring.boot.starter.configuration.RmtImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Configuration
@EnableConfigurationProperties(RmtStarterProperties.class)
@ConditionalOnClass(RequestMappingHandlerMapping.class)
@ConditionalOnProperty(prefix = "rmt.starter", name = "enable", havingValue = "true")
@Import({RmtImportSelector.class})
public class RmtStarterAutoConfiguration {

}
