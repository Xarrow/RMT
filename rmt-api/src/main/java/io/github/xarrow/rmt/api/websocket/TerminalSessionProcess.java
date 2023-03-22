package io.github.xarrow.rmt.api.websocket;

import com.pty4j.PtyProcess;
import com.pty4j.WinSize;
import com.sun.jna.Platform;
import io.github.xarrow.rmt.api.exception.TerminalProcessException;
import io.github.xarrow.rmt.api.lifecycle.AbstractTerminalProcess;
import io.github.xarrow.rmt.api.protocol.TerminalCommandQueue;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import io.github.xarrow.rmt.api.session.ProcessWrapper;
import io.github.xarrow.rmt.api.session.TerminalContext;
import io.github.xarrow.rmt.api.session.TerminalContextManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static io.github.xarrow.rmt.commons.TerminalThreadHelper.*;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Slf4j
public class TerminalSessionProcess extends AbstractTerminalProcess {
    protected TerminalProcessExtend terminalProcessExtend;

    private TerminalCommandQueue<String> terminalCommandQueue;
    private TerminalContextManager terminalContextManager;

    public void setTerminalProcessExtend(TerminalProcessExtend terminalProcessExtend) {
        this.terminalProcessExtend = terminalProcessExtend;
        this.terminalCommandQueue = terminalProcessExtend.getTerminalCommandQueue();
        this.terminalContextManager = terminalProcessExtend.getTerminalContextManager();
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
        // 5. before command execute listener, you can modify command via this expand
        byte[] commandBytes = command.getBytes();
        terminalProcessExtend.doBeforeCommandInterceptor(commandBytes);
        command = new String(commandBytes);
        // put into queue , do async action
        terminalCommandQueue.putMessage(command);

        // TODO
        // get from queue
        String commandMessage = terminalCommandQueue.pollMessage();
        // 6. before command interceptor
        writeHandlerThreadPool.submit(new WriteToProcessBufferedThread(this.stdout, commandMessage, terminalProcessExtend));
    }

    @Override
    public void terminalHeartbeat(WebSocketSession webSocketSession, TerminalMessage terminalMessage) {
        heartbeatHandlerThreadPool.submit(
                new HeartbeatBufferedReaderThread(this.stdin, webSocketSession));
    }

    @Override
    public void terminalClose(WebSocketSession webSocketSession, final TerminalMessage message) {
        try {
            webSocketSession.close();
            if (null == this.process || !this.process.isAlive()) {
                return;
            }
            this.process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            terminalProcessExtend.doClosedListener(webSocketSession, message);
        }
    }


    protected void doInitBash(WebSocketSession session, TerminalMessage message)
            throws IOException, TerminalProcessException {
        if (this.init) {
            return;
        }
        //2. before init
        terminalProcessExtend.doBeforeInitListener(message);
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
        // get process from manager cache
        if (null != terminalContext && null != terminalContext.processWrapper()) {
            ProcessWrapper processWrapper = terminalContext.processWrapper();
            if (null != processWrapper && null != processWrapper.process() && processWrapper.process().isAlive()) {
                if (processWrapper.process() instanceof PtyProcess) {
                    process = (PtyProcess) processWrapper.process();
                }
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
                        session,
                        terminalProcessExtend
                ));
        //just print to web
        readerHandlerThreadPool.submit(
                new RespondToWebBufferedThread(
                        this.stdin,
                        session,
                        terminalProcessExtend
                ));

        // 3. after init
        terminalProcessExtend.doAfterInitListener(message, process);
        // 4. lifecycle listener
        terminalProcessExtend.doLifeCycleListener(this);

        terminalContextManager.buildTerminalContext();
    }
}
