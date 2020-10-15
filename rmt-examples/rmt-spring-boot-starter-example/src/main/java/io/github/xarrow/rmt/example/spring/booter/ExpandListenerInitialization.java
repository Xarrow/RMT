package io.github.xarrow.rmt.example.spring.booter;

import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.expand.listener.AppStartBannerLoadListener;
import io.github.xarrow.rmt.expand.listener.WindowsEmbeddedPythonEnvLoadListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 10/14/2020.
 * @Desc:
 */

public class ExpandListenerInitialization implements InitializingBean {

    @Autowired
    private TerminalProcessListenerManager terminalProcessListenerManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        terminalProcessListenerManager.registerListener(new AppStartBannerLoadListener());
        terminalProcessListenerManager.registerListener(new WindowsEmbeddedPythonEnvLoadListener());
    }
}
