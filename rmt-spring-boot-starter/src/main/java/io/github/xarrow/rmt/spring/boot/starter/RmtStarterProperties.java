package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.api.client.RmtClientProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.github.xarrow.rmt.commons.RmtCommons.DEFAULT_WEBSOCKET_PATH;
import static io.github.xarrow.rmt.commons.RmtCommons.DEFAULT_WEB_PATH;

@ConfigurationProperties(prefix = "rmt.starter")
@Accessors(chain = true)
@Data
public class RmtStarterProperties extends RmtClientProperties {
    /**
     * 二级目录
     */
    private String webPath = DEFAULT_WEB_PATH;

    /**
     * websocket 目录
     */
    private String websocketPath = DEFAULT_WEBSOCKET_PATH;

}
