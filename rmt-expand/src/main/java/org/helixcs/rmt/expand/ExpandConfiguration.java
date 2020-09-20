package org.helixcs.rmt.expand;

import org.helixcs.rmt.api.listener.TerminalProcessListener;
import org.helixcs.rmt.expand.listener.AppStartBannerLoadListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
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
