package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import io.github.xarrow.rmt.api.listener.DefaultTerminalListenerManager;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.DefaultTerminalCommandQueue;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import io.github.xarrow.rmt.api.session.*;
import io.github.xarrow.rmt.api.session.DefaultTerminalContextManager;
import io.github.xarrow.rmt.api.session.TerminalContextManager;
import io.github.xarrow.rmt.api.websocket.TerminalProcessExtend;
import io.github.xarrow.rmt.api.websocket.TerminalTextWebSocketHandler;
import io.github.xarrow.rmt.api.websocket.TerminalSessionProcess;
import io.github.xarrow.rmt.expand.listener.AppBannerLoadListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: helix
 * @Time:2022/7/30
 * @Site: https://github.com/xarrow
 * <p>
 */
public class RmtConfiguration {

    @Bean("appBannerLoadListener")
    public AppBannerLoadListener appBannerLoadListener() {
        return new AppBannerLoadListener();
    }

    // 监听器管理
    @Bean("terminalProcessListenerManager")
    public TerminalProcessListenerManager terminalProcessListenerManager
    (@Qualifier("appBannerLoadListener") AppBannerLoadListener appBannerLoadListener) {
        DefaultTerminalListenerManager defaultTerminalListenerManager = new DefaultTerminalListenerManager();
        defaultTerminalListenerManager.registerListener(appBannerLoadListener);
        return defaultTerminalListenerManager;
    }

    // messageQueue
    @Bean(name = "terminalMessageQueue")
    public TerminalCommandQueue<String> terminalMessageQueue() {
        return new DefaultTerminalCommandQueue();
    }

    // sessionManager
    @Bean(value = "terminalContextManager")
    public TerminalContextManager terminalContextManager() {
        return new DefaultTerminalContextManager();
    }

    // session2processManager
//    @Bean
//    public TerminalSession2ProcessManager terminalSession2ProcessManager() {
//        return new DefaultTerminalSession2ProcessManager();
//    }
    @Bean(name = "terminalProcess")
    @Scope("prototype")
    public TerminalProcess terminalProcess(@Qualifier("terminalMessageQueue") TerminalCommandQueue<String> terminalCommandQueue,
                                           @Qualifier("terminalContextManager") TerminalContextManager terminalContextManager,
                                           @Qualifier("terminalProcessListenerManager") TerminalProcessListenerManager terminalProcessListenerManager) {
        TerminalSessionProcess terminalSessionProcess = new TerminalSessionProcess();
        TerminalProcessExtend terminalProcessExtend = new TerminalProcessExtend();

        terminalProcessExtend.setTerminalCommandQueue(terminalCommandQueue);
        terminalProcessExtend.setTerminalProcessListenerManager(terminalProcessListenerManager);
        terminalProcessExtend.setTerminalContextManager(terminalContextManager);
        terminalSessionProcess.setTerminalProcessExtend(terminalProcessExtend);

        return terminalSessionProcess;
    }

    @Bean(name = "terminalWebSocketHandler")
    public WebSocketHandler terminalWebSocketHandler() {
        return new PerConnectionWebSocketHandler(TerminalTextWebSocketHandler.class);
    }
}
