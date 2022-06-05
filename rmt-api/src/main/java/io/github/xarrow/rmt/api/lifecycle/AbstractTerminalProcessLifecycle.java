package io.github.xarrow.rmt.api.lifecycle;

import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.session.SessionWrapper;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.DefaultStringBindKey;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.session.ProcessWrapper;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessMap;
import io.github.xarrow.rmt.api.protocol.TerminalMessageQueue;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
public abstract class AbstractTerminalProcessLifecycle implements TerminalProcessLifecycle {
    protected BufferedReader stderr;
    protected BufferedWriter stdout;
    protected BufferedReader stdin;

    protected volatile boolean init;
    protected int columns = 100;
    protected int rows = 20;
    protected int width = 20;
    protected int height = 20;

    protected TerminalProcessListenerManager terminalProcessListenerManager;
    protected TerminalMessageQueue<String> terminalMessageQueue;
    protected TerminalSession2ProcessManager terminalSession2ProcessManager;

    public void setTerminalProcessListenerManager(
        TerminalProcessListenerManager terminalProcessListenerManager) {
        this.terminalProcessListenerManager = terminalProcessListenerManager;
    }

    public void setTerminalMessageQueue(TerminalMessageQueue<String> terminalMessageQueue) {
        this.terminalMessageQueue = terminalMessageQueue;
    }

    public void setTerminalSession2ProcessManager(TerminalSession2ProcessManager terminalSession2ProcessManager) {
        this.terminalSession2ProcessManager = terminalSession2ProcessManager;
    }

    protected void doInit() throws IOException {
    }

    public void doPrepareListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.prepared(terminalMessage));
    }

    public void doBeforeInitListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.beforeInit(terminalMessage));
    }

    public void doAfterInitListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach(
            (key, value) -> value
                .afterInit(terminalMessage, currentSession(), currentProcess(), stdout, stdin, stderr));
    }

    public void doBeforeCommandListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.beforeCommand(terminalMessage));
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

    public void doCloseListener(final TerminalMessage terminalMessage) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.closed(terminalMessage));
    }

    public void doLifeCycleListener(TerminalProcessLifecycle lifecycle) {
        terminalProcessListenerManager.listenerMap().forEach((key, value) -> value.lifeCycleContext(lifecycle));
    }

    protected abstract WebSocketSession currentSession();

    protected abstract PtyProcess currentProcess();

    public void doTerminalSession2ProcessBind() {
        terminalSession2ProcessManager.session2ProcessBind(new DefaultStringBindKey().setKey(currentSession().getId()),
            new TerminalSession2ProcessMap() {
                @Override
                public SessionWrapper sessionWrapper() {
                    return new SessionWrapper() {
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
                        public PtyProcess ptyProcess() {
                            return currentProcess();
                        }
                    };
                }
            });
    }
}
