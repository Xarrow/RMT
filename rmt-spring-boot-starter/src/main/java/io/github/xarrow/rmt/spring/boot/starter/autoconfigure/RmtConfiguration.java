package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import io.github.xarrow.rmt.api.listener.DefaultTerminalListenerManager;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.DefaultTerminalCommandQueue;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import io.github.xarrow.rmt.api.session.*;
import io.github.xarrow.rmt.api.session.DefaultTerminalContextManager;
import io.github.xarrow.rmt.api.session.TerminalContextManager;
import io.github.xarrow.rmt.api.websocket.TerminalTextWebSocketHandler;
import io.github.xarrow.rmt.api.websocket.TerminalSessionProcess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * @Author: helix
 * @Time:2022/7/30
 * @Site: https://github.com/xarrow
 * <p>
 * todo  拓展
 */
public class RmtConfiguration {
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PerConnectionWebSocketHandler(TerminalTextWebSocketHandler.class);
    }

    /**
     * lifecycle
     *
     * @return
     */
    @Bean(value = "terminalProcessLifecycle")
    @Scope("prototype")
    public TerminalProcess terminalProcessLifecycle() {
        return new TerminalSessionProcess();
    }

    // 监听器管理
    @Bean
    public TerminalProcessListenerManager terminalProcessListenerManager() {
        return new DefaultTerminalListenerManager();
    }

    // messageQueue
    @Bean
    public TerminalCommandQueue<String> terminalMessageQueue() {
        return new DefaultTerminalCommandQueue();
    }

    // sessionManager
    @Bean(value = "terminalSessionManager")
    public TerminalContextManager terminalSessionManager() {
        return new DefaultTerminalContextManager();
    }

    // session2processManager
    @Bean
    public TerminalSession2ProcessManager terminalSession2ProcessManager() {
        return new DefaultTerminalSession2ProcessManager();
    }
}
