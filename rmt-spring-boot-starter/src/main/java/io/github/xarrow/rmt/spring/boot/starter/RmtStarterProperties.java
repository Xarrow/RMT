package io.github.xarrow.rmt.spring.boot.starter;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rmt.starter")
@Data
@Accessors(chain = true)
public class RmtStarterProperties {

    private String web;
}
