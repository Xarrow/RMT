package io.github.xarrow.rmt.api.websocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.pty4j.PtyProcess;
import com.pty4j.WinSize;
import com.sun.jna.Platform;
import io.github.xarrow.rmt.api.PtyProcessPool;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import lombok.extern.slf4j.Slf4j;
import io.github.xarrow.rmt.api.lifecycle.AbstractTerminalProcessLifecycle;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.protocol.TerminalMessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import static io.github.xarrow.rmt.commons.TerminalThreadHelper.errorHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.heartbeatHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.readerHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.writeHandlerThreadPool;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Slf4j
public class TerminalWsSessionProcessLifecycle extends AbstractTerminalProcessLifecycle {
    protected PtyProcess ptyProcess;
    private WebSocketSession webSocketSession;

    @Override
    public void terminalConnection(WebSocketSession session) {
        this.webSocketSession = session;
    }

    @Override
    public void terminalReady(WebSocketSession session, final TerminalMessage message) throws IOException {
        doInit();
    }

    @Override
    public void terminalInit(WebSocketSession session, final TerminalMessage message) throws IOException {
        doInit();
    }

    @Override
    public void terminalResize(WebSocketSession session, final TerminalMessage message) throws IOException {
        if (!init) {
            doInit();
        }
        int columns = ((TerminalRQ) message).getCols();
        //compatible
        if (columns < 1) {
            columns = ((TerminalRQ) message).getColumns();
        }
        if (columns > 0) {
            this.columns = columns;
        }
        int rows = ((TerminalRQ) message).getRows();
        if (rows > 0) {
            this.rows = rows;
        }
        this.ptyProcess.setWinSize(new WinSize(this.columns, this.rows));
    }

    @Override
    public void terminalCommand(WebSocketSession session, final TerminalMessage message) throws InterruptedException, IOException {
        if (!init) {
            doInit();
        }
        String command = ((TerminalRQ) message).getCommand();
        doBeforeCommandListener(message);
        // 加入队列
        terminalMessageQueue.putMessage(command);

        writeHandlerThreadPool.submit(
                new BufferedWriteThread()
                        .setCommand(terminalMessageQueue.pollMessage())
                        .setManager(terminalProcessListenerManager)
                        .setBufferedWriter(this.stdout)
        );
    }

    @Override
    public void terminalHeartbeat(WebSocketSession session, TerminalMessage terminalMessage) {
        heartbeatHandlerThreadPool.submit(
                new HeartbeatBufferedReaderThread()
                        .setManager(terminalProcessListenerManager)
                        .setWebSocketSession(webSocketSession));
    }

    @Override
    public void terminalClose(WebSocketSession session, final TerminalMessage message) {
        if (null == this.ptyProcess || !this.ptyProcess.isAlive()) {
            return;
        }
        this.ptyProcess.destroy();
        doCloseListener(message);
    }

    private static final PtyProcessPool pool = new PtyProcessPool(7);

    @Override
    protected void doInit() throws IOException {
        //1.before init
        doBeforeInitListener(null);

        if (this.init) {
            return;
        }
        // directory
        String userHome = System.getProperty("user.home");
        // cmd
        String[] command;
        // windows
        if (Platform.isWindows()) {
            command = "cmd.exe".split("\\s+");
        }
        // linux
        else {
            command = "/bin/bash -i".split("\\s+");
        }
        Map<String, String> envs = new HashMap<String, String>(System.getenv()) {{
            put("TERM", "xterm");
        }};
        this.ptyProcess = PtyProcess.exec(command, envs, userHome);

        this.stderr = new BufferedReader(new InputStreamReader(this.ptyProcess.getErrorStream()));
        this.stdin = new BufferedReader(new InputStreamReader(this.ptyProcess.getInputStream()));
        this.stdout = new BufferedWriter(new OutputStreamWriter(this.ptyProcess.getOutputStream()));

        this.init = true;
        // after init
        doAfterInitListener(null);

        // always with session
        errorHandlerThreadPool.submit(
                new BufferedReaderThread()
                        .setMessageType(AbstractTerminalStructure.MessageType.TERMINAL_PRINT)
                        .setManager(terminalProcessListenerManager)
                        .setBufferedReader(this.stderr)
                        .setWebSocketSession(webSocketSession));
        readerHandlerThreadPool.submit(
                new BufferedReaderThread()
                        .setMessageType(AbstractTerminalStructure.MessageType.TERMINAL_PRINT)
                        .setManager(terminalProcessListenerManager)
                        .setBufferedReader(this.stdin)
                        .setWebSocketSession(webSocketSession));

        // lifecycle listener
        doLifeCycleListener(this);
        // session2Process bind
        doTerminalSession2ProcessBind();

    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    @Autowired
    public void setTerminalProcessListenerManager(TerminalProcessListenerManager terminalProcessListenerManager) {
        this.terminalProcessListenerManager = terminalProcessListenerManager;
    }

    @Autowired
    public void setTerminalMessageQueue(TerminalMessageQueue<String> terminalMessageQueue) {
        this.terminalMessageQueue = terminalMessageQueue;
    }

    @Autowired
    public void setTerminalSession2ProcessManager(TerminalSession2ProcessManager terminalSession2ProcessManager) {
        super.setTerminalSession2ProcessManager(terminalSession2ProcessManager);
    }

    @Override
    protected WebSocketSession currentSession() {
        return webSocketSession;
    }

    @Override
    protected PtyProcess currentProcess() {
        return ptyProcess;
    }
}
