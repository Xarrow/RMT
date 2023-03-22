package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.session.TerminalContext;
import io.github.xarrow.rmt.api.session.TerminalContextManager;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class TerminalProcessExtend {
    // terminal command queue
    protected TerminalCommandQueue<String> terminalCommandQueue;
    // terminal process listener manager
    protected TerminalProcessListenerManager terminalProcessListenerManager;
    // terminal context manager
    protected TerminalContextManager terminalContextManager;

    public void doSessionConnectListener(WebSocketSession webSocketSession) {
        terminalProcessListenerManager
                .allListenerMap()
                .forEach((key, value)
                        -> value.sessionConnect(webSocketSession));
    }


    public void doBeforeInitListener(TerminalMessage terminalMessage) {
        terminalProcessListenerManager
                .allListenerMap()
                .forEach((key, value)
                        -> value.beforeInit(terminalMessage));
    }

    public void doAfterInitListener(TerminalMessage terminalMessage, Process process) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) ->
                value.afterInit(terminalMessage, process));
    }

    public void doBeforeCommandInterceptor(byte[] commandBytes) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.beforeCommandInterceptor(commandBytes));
    }

    public void doAfterCommandListener(TerminalMessage terminalMessage) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.afterCommand(terminalMessage));
    }

    public void doClosedListener(WebSocketSession webSocketSession, TerminalMessage terminalMessage) {
        // terminalContext remove
        TerminalContext terminalContext = terminalContextManager.getTerminalContext(webSocketSession.getId());
        terminalContextManager.removeTerminalContext(terminalContext);

        // terminal Listener remove
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.closed(terminalMessage));
    }

    public void doLifeCycleListener(TerminalProcess lifecycle) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.lifeCycleContext(lifecycle));
    }
}
