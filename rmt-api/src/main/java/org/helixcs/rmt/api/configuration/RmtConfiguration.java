package org.helixcs.rmt.api.configuration;

import org.helixcs.rmt.api.lifecycle.TerminalProcessLifecycle;
import org.helixcs.rmt.api.listener.DefaultTerminalListenerManager;
import org.helixcs.rmt.api.listener.TerminalProcessListener;
import org.helixcs.rmt.api.listener.TerminalProcessListenerManager;
import org.helixcs.rmt.api.protocol.DefaultTerminalMessageQueue;
import org.helixcs.rmt.api.protocol.TerminalMessageQueue;
import org.helixcs.rmt.api.session.DefaultTerminalSession2ProcessManager;
import org.helixcs.rmt.api.session.DefaultTerminalSessionManager;
import org.helixcs.rmt.api.session.TerminalSession2ProcessManager;
import org.helixcs.rmt.api.session.TerminalSessionManager;
import org.helixcs.rmt.api.websocket.TerminalHandler;
import org.helixcs.rmt.api.websocket.TerminalWsSessionProcessLifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Configuration
@EnableWebSocket
public class RmtConfiguration implements WebSocketConfigurer {

    //@Autowired
    //private TerminalProcessListener appStartBannerLoadListener;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler(), "/terminal").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PerConnectionWebSocketHandler(TerminalHandler.class);
    }

    // lifecycle
    @Bean
    @Scope("prototype")
    public TerminalProcessLifecycle terminalProcessLifecycle() {
        return new TerminalWsSessionProcessLifecycle();
    }

    // 监听器管理
    @Bean
    public TerminalProcessListenerManager terminalProcessListenerManager() {
        DefaultTerminalListenerManager listenerManager = new DefaultTerminalListenerManager();
        //listenerManager.registerListener(appStartBannerLoadListener);
        //listenerManager.registerListener(new WindowsExpandCommandLoaderListener());
        return listenerManager;
    }

    // messageQueue
    @Bean
    public TerminalMessageQueue<String> terminalMessageQueue() {
        return new DefaultTerminalMessageQueue();
    }

    // sessionManager
    @Bean
    public TerminalSessionManager terminalSessionManager() {
        return new DefaultTerminalSessionManager();
    }

    // session2processManager
    @Bean
    public TerminalSession2ProcessManager terminalSession2ProcessManager() {
        return new DefaultTerminalSession2ProcessManager();
    }

}
