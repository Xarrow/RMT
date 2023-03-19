package io.github.xarrow.rmt.api.websocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.pty4j.PtyProcess;
import com.pty4j.WinSize;
import com.sun.jna.Platform;
import io.github.xarrow.rmt.api.exception.TerminalProcessException;
import io.github.xarrow.rmt.api.session.ProcessWrapper;
import io.github.xarrow.rmt.api.session.TerminalContext;
import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
import lombok.extern.slf4j.Slf4j;
import io.github.xarrow.rmt.api.lifecycle.AbstractTerminalProcess;
import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import org.springframework.web.socket.WebSocketSession;

import static io.github.xarrow.rmt.commons.TerminalThreadHelper.errorHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.heartbeatHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.readerHandlerThreadPool;
import static io.github.xarrow.rmt.commons.TerminalThreadHelper.writeHandlerThreadPool;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Slf4j
public class TerminalSessionProcess extends AbstractTerminalProcess {
    private TerminalProcessExtend terminalProcessExtend;
    private TerminalCommandQueue<String> terminalCommandQueue;
    private TerminalProcessListenerManager terminalProcessListenerManager;

    public void setTerminalProcessExtend(TerminalProcessExtend terminalProcessExtend) {
        this.terminalProcessExtend = terminalProcessExtend;
        this.terminalCommandQueue = terminalProcessExtend.getTerminalCommandQueue();
        this.terminalProcessListenerManager = terminalProcessExtend.getTerminalProcessListenerManager();
    }

    /**
     * create websocket session
     * 1. register session connection
     * 2. session create event notify
     *
     * @param session
     */
    @Override
    public void terminalConnection(WebSocketSession session) {
        // todo register session connection
        // 1.  session create listener notify
        terminalProcessExtend.doSessionConnectListener(session);
    }

    @Override
    public void terminalReady(WebSocketSession session, TerminalMessage message) throws TerminalProcessException, IOException {
        // init
        doInitBash(session, message);
    }

    @Override
    public void terminalInit(WebSocketSession session, TerminalMessage message) throws IOException, TerminalProcessException {
        // init bash
        doInitBash(session, message);
    }

    @Override
    public void terminalResize(WebSocketSession session, TerminalMessage message) throws IOException, TerminalProcessException {
        if (!init) {
            doInitBash(session, message);
        }
        int columns = ((TerminalRQ) message).getColumns();
        // columns
        if (columns < 1) {
            columns = this.columns;
        }
        this.columns = columns;
        // rows
        int rows = ((TerminalRQ) message).getRows();
        if (rows < 1) {
            rows = this.rows;
        }
        this.rows = rows;
        // resize
        this.process.setWinSize(new WinSize(this.columns, this.rows));
    }

    @Override
    public void terminalCommand(WebSocketSession session, TerminalMessage message) throws InterruptedException, IOException, TerminalProcessException {
        if (!init) {
            doInitBash(session, message);
        }
        String command = ((TerminalRQ) message).getCommand();
        // 5. before command execute listener
        terminalProcessExtend.doBeforeCommandListener(message);
        // put into queue , do async action
        terminalCommandQueue.putMessage(command);

        // TODO
        // get from queue
        String commandMessage = terminalCommandQueue.pollMessage();
        // 6. before command interceptor
        terminalProcessExtend.doBeforeCommandInterceptor(commandMessage.getBytes(Charset.defaultCharset()));
        writeHandlerThreadPool.submit(new WriteToProcessBufferedThread(this.stdout, commandMessage));
    }

    @Override
    public void terminalHeartbeat(WebSocketSession webSocketSession, TerminalMessage terminalMessage) {
        heartbeatHandlerThreadPool.submit(new HeartbeatBufferedReaderThread().setTerminalProcessListenerManager(terminalProcessListenerManager).setWebSocketSession(webSocketSession));
    }

    @Override
    public void terminalClose(WebSocketSession webSocketSession, final TerminalMessage message) {
        try {
            webSocketSession.close();

            if (null == this.ptyProcess || !this.ptyProcess.isAlive()) {
                return;
            }
            this.ptyProcess.destroy();
        } catch (IOException e) {
            log.error("");
        } finally {
            doCloseNotify(message);
            terminalContextManager.removeSession(new TerminalContext() {
                @Override
                public WebSocketSession webSocketSession() {
                    return webSocketSession;
                }
            });
        }
    }


    protected void doInitBash(WebSocketSession session, TerminalMessage message) throws IOException, TerminalProcessException {
        if (this.init) {
            return;
        }
        //2. before init
        doBeforeInitListener(message);
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

        PtyProcess process = null;
        TerminalContext terminalContext = terminalContextManager.getTerminalContext(session.getId());
        ProcessWrapper processWrapper = terminalContext.process();
        // get process from manager cache
        if (null != processWrapper && processWrapper.process().isAlive()) {
            Process processInWrapper = processWrapper.process();
            if (processInWrapper instanceof PtyProcess) {
                process = (PtyProcess) processInWrapper;
            }
        } else {
            process = PtyProcess.exec(command, envs, userHome);
        }
        if (null == process) {
            throw new TerminalProcessException("Process not support");
        }
        this.process = process;
        // error bufferReader,print to web
        this.stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        // in bufferReader, print to web
        this.stdin = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // out bufferWriter, write to Process
        this.stdout = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        this.init = true;

        // just print to web
        errorHandlerThreadPool.submit(
                new RespondToWebBufferedThread(
                        this.stderr,
                        session));
        //just print to web
        readerHandlerThreadPool.submit(
                new RespondToWebBufferedThread(
                        this.stdin,
                        session
                )
        );

        // 3. after init
        doAfterInitListener(message, process);
        // 4. lifecycle listener
        doLifeCycleListener(this);
    }


    public void setTerminalProcessListenerManager(TerminalProcessListenerManager terminalProcessListenerManager) {
        this.terminalProcessListenerManager = terminalProcessListenerManager;
    }

    public void setTerminalMessageQueue(TerminalCommandQueue<String> terminalCommandQueue) {
        this.terminalMessageQueue = terminalCommandQueue;
    }

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
