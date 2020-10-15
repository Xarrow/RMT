package io.github.xarrow.rmt.expand.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.lifecycle.TerminalProcessLifecycle;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 10/14/2020.
 * @Desc:
 */
public class EmbeddedPythonEnvLoadListener implements TerminalProcessListener {
    @Override
    public String listenerName() {
        return EmbeddedPythonEnvLoadListener.class.getSimpleName();
    }

    @SneakyThrows
    @Override
    public void afterInit(TerminalMessage message,
                          WebSocketSession socketSession,
                          PtyProcess ptyProcess,
                          BufferedWriter stdout,
                          BufferedReader stdin,
                          BufferedReader stderr) {

        String pythonEnvCmd = new ClassPathResource("python-3.9.0-embed-amd64/rmt_py_env.cmd").getFile()
            .getAbsolutePath();
        stdout.write(pythonEnvCmd + "\r");
        stdout.flush();
    }

    @Override
    public void lifeCycleContext(TerminalProcessLifecycle terminalProcessLifecycle) {

    }
}
