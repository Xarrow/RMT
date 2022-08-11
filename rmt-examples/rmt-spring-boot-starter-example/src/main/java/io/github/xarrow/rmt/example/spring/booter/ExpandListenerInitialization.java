package io.github.xarrow.rmt.example.spring.booter;

import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.expand.listener.AppBasicLoadListener;
import io.github.xarrow.rmt.expand.listener.TestLocalListener;
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
        AppBasicLoadListener appBasicLoadListener = new AppBasicLoadListener();
        appBasicLoadListener.setSpringApplicationContext(applicationContext);
        // APP 基础信息
        terminalProcessListenerManager.registerListener(appBasicLoadListener);
        // 测试
        terminalProcessListenerManager.registerListener(new TestLocalListener());
//        terminalProcessListenerManager.registerListener(new WindowsEmbeddedPythonEnvLoadListener());
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
