package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.api.client.RmtClientProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rmt.starter")
@Accessors(chain = true)
@Data
public class RmtStarterProperties extends RmtClientProperties {
    private static final String DEFAULT_WEB_PATH = "rmt";
    private static final String DEFAULT_WEBSOCKET_PATH = "terminal";

    /**
     * 二级目录
     */
    private String webPath = DEFAULT_WEB_PATH;

    /**
     * websocket 目录
     */
    private String websocketPath = DEFAULT_WEBSOCKET_PATH;

}
