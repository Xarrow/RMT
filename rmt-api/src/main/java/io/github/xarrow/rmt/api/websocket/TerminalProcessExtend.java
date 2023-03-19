package io.github.xarrow.rmt.api.websocket;

import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.DefaultStringBindKey;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import io.github.xarrow.rmt.api.session.ProcessWrapper;
import io.github.xarrow.rmt.api.session.TerminalContext;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessMap;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class TerminalProcessExtend {
    // terminal command queue
    protected TerminalCommandQueue<String> terminalCommandQueue;
    // terminal process listener manager
    protected TerminalProcessListenerManager terminalProcessListenerManager;
    protected TerminalSession2ProcessManager terminalSession2ProcessManager;


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
        terminalProcessListenerManager.allListenerMap().forEach(
                (key, value) ->
                        value.afterInit(terminalMessage, process));
    }

    public void doBeforeCommandListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.beforeCommand(terminalMessage));
    }
    //public void doRequestToPytListener(final TerminalMessage terminalMessage) {
    //    terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.responseFromPty(terminalMessage));
    //}
    //public void doResponseFromPytListener(final TerminalMessage terminalMessage) {
    //    terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.responseFromPty(terminalMessage));
    //}

    public void doAfterCommandListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.afterCommand(terminalMessage));
    }

    public void doCloseNotify(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.closed(terminalMessage));
    }

    public void doLifeCycleListener(TerminalProcess lifecycle) {
        terminalProcessListenerManager.allListenerMap().forEach((key, value) -> value.lifeCycleContext(lifecycle));
    }


    public void doTerminalSession2ProcessBind() {
        terminalSession2ProcessManager.session2ProcessBind(new DefaultStringBindKey().setKey(currentSession().getId()),
                new TerminalSession2ProcessMap() {
                    @Override
                    public TerminalContext sessionWrapper() {
                        return new TerminalContext() {
                            @Override
                            public WebSocketSession webSocketSession() {
                                return currentSession();
                            }
                        };
                    }

                    @Override
                    public ProcessWrapper processWrapper() {
                        return new ProcessWrapper() {
                            @Override
                            public PtyProcess process() {
                                return currentProcess();
                            }
                        };
                    }
                });
    }
}
