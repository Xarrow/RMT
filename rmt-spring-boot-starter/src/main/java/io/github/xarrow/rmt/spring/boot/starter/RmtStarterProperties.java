package io.github.xarrow.rmt.spring.boot.starter;

import io.github.xarrow.rmt.api.client.RmtClientProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.github.xarrow.rmt.commons.RmtCommons.*;

@ConfigurationProperties(prefix = "rmt.starter")
@Accessors(chain = true)
@Data
public class RmtStarterProperties extends RmtClientProperties {
    /**
     * 二级访问路径
     */
    private String webPath = DEFAULT_WEB_PATH;

    /**
     * websocket 访问路径
     */
    private String websocketPath = DEFAULT_WEBSOCKET_PATH;

    /**
     * 是否开启静态文件服务
     */
    private boolean enableStaticFileService = false;
    /**
     * 静态资源路径
     */
    private String staticWebPath = DEFAULT_STATIC_FILE_WEB_PATH;

    /**
     * 静态资源本地路径
     */
    private String staticFileLocation = DEFAULT_STATIC_FILE_LOCATION;

    /**
     * 静态资源访问view
     */
    private String staticFileServiceViewPath = DEFAULT_STATIC_FILE_VIEW_PATH;

}
