package io.github.xarrow.rmt.api.listener;

import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.session.ProcessWrapper;
import io.github.xarrow.rmt.api.session.TerminalContext;
import io.github.xarrow.rmt.api.session.TerminalContextManager;
import org.springframework.web.socket.WebSocketSession;

/**
 * TerminalContextListener
 * Set `TerminalContext` by `TerminalProcessListener`
 */
public class TerminalContextListener implements TerminalProcessListener, TerminalContext {
    private WebSocketSession session;
    private Process process;

    @Override
    public void sessionConnect(WebSocketSession webSocketSession) {
        this.session = webSocketSession;
    }

    @Override
    public void afterInit(TerminalMessage message, Process process) {
        this.process = process;
    }

    @Override
    public WebSocketSession session() {
        return this.session;
    }

    @Override
    public ProcessWrapper processWrapper() {
        return () -> process;
    }
}
