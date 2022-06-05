package io.github.xarrow.rmt.expand;

import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.expand.listener.AppStartBannerLoadListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Email: zhangjian12424@gmail.com .
 * @Author: helixcs
 * @Date: 6/24/2020.
 * @Desc:
 */

@Configuration
public class ExpandConfiguration {
    @Bean
    public TerminalProcessListener appStartBannerLoadListener() {
        return new AppStartBannerLoadListener();
    }
}
