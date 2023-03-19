package io.github.xarrow.rmt.api.lifecycle;

import com.pty4j.PtyProcess;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
public abstract class AbstractTerminalProcess implements TerminalProcess {
    // process
    protected PtyProcess process;
    // error buffer
    protected BufferedReader stderr;
    // writer to pty
    protected BufferedWriter stdout;
    // read from pty
    protected BufferedReader stdin;

    protected volatile boolean init;
    protected int columns = 100;
    protected int rows = 20;
    protected int width = 20;
    protected int height = 20;

    protected abstract WebSocketSession currentSession();

    protected abstract PtyProcess currentProcess();

}
