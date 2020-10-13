package io.github.xarrow.rmt.expand.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import com.pty4j.PtyProcess;
import com.sun.jna.Platform;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 6/23/2020.
 * @Desc: Windows 拓展命令加载 Listener
 */
public class WindowsExpandCommandLoaderListener implements TerminalProcessListener {

    @Override
    public String listenerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void afterInit(TerminalMessage message, WebSocketSession socketSession,
                          PtyProcess ptyProcess, BufferedWriter stdout, BufferedReader stdin,
                          BufferedReader stderr) {

        if (Platform.isWindows()) {
            File file = new File("win_extends");
            try {
                stdout.write(MessageFormat.format("SET PATH={0};%PATH%;\r", file.getAbsolutePath()));
                stdout.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
