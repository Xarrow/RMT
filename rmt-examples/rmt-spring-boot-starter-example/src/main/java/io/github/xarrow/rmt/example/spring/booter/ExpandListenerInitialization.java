package io.github.xarrow.rmt.example.spring.booter;

import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.expand.listener.AppStartBannerLoadListener;
import io.github.xarrow.rmt.expand.listener.TestLocalListener;
import io.github.xarrow.rmt.expand.listener.WindowsEmbeddedPythonEnvLoadListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Email: zhangjian12424@gmail.com .
 * @Author: helixcs
 * @Date: 10/14/2020.
 * @Desc:
 */

public class ExpandListenerInitialization implements InitializingBean, ApplicationContextAware {

    @Autowired
    private TerminalProcessListenerManager terminalProcessListenerManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        AppStartBannerLoadListener appStartBannerLoadListener = new AppStartBannerLoadListener();
        appStartBannerLoadListener.setSpringApplicationContext(applicationContext);
        terminalProcessListenerManager.registerListener(appStartBannerLoadListener);
        terminalProcessListenerManager.registerListener(new TestLocalListener());
//        terminalProcessListenerManager.registerListener(new WindowsEmbeddedPythonEnvLoadListener());
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
