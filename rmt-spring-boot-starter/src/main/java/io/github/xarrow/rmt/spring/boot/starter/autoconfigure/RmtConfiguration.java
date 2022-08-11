package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcessLifecycle;
import io.github.xarrow.rmt.api.listener.DefaultTerminalListenerManager;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.DefaultTerminalMessageQueue;
import io.github.xarrow.rmt.api.protocol.TerminalMessageQueue;
import io.github.xarrow.rmt.api.session.DefaultTerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.session.DefaultTerminalSessionManager;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.session.TerminalSessionManager;
import io.github.xarrow.rmt.api.websocket.TerminalHandler;
import io.github.xarrow.rmt.api.websocket.TerminalWsSessionProcessLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * @Author: helix
 * @Time:2022/7/30
 * @Site: http://iliangqunru.bitcron.com/
 * <p>
 * todo  拓展
 */
public class RmtConfiguration {
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PerConnectionWebSocketHandler(TerminalHandler.class);
    }

    /**
     * lifecycle
     *
     * @return
     */
    @Bean(value = "terminalProcessLifecycle")
    @Scope("prototype")
    public TerminalProcessLifecycle terminalProcessLifecycle() {
        return new TerminalWsSessionProcessLifecycle();
    }

    // 监听器管理
    @Bean
    public TerminalProcessListenerManager terminalProcessListenerManager() {
        return new DefaultTerminalListenerManager();
    }

    // messageQueue
    @Bean
    public TerminalMessageQueue<String> terminalMessageQueue() {
        return new DefaultTerminalMessageQueue();
    }

    // sessionManager
    @Bean(value = "terminalSessionManager")
    public TerminalSessionManager terminalSessionManager() {
        return new DefaultTerminalSessionManager();
    }

    // session2processManager
    @Bean
    public TerminalSession2ProcessManager terminalSession2ProcessManager() {
        return new DefaultTerminalSession2ProcessManager();
    }
}
