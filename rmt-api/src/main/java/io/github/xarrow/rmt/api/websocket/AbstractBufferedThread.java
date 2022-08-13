package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.listener.TerminalProcessListenerManager;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public abstract class AbstractBufferedThread extends Thread {
    protected TerminalProcessListenerManager terminalProcessListenerManager;
    protected WebSocketSession webSocketSession;
    protected BufferedReader bufferedReader;
    protected BufferedWriter bufferedWriter;

    public AbstractBufferedThread setTerminalProcessListenerManager(TerminalProcessListenerManager terminalProcessListenerManager) {
        this.terminalProcessListenerManager = terminalProcessListenerManager;
        return this;
    }

    public AbstractBufferedThread setWebSocketSession(final WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        return this;
    }

    public AbstractBufferedThread setBufferedReader(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        return this;
    }

    public AbstractBufferedThread setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
        return this;
    }

    @SneakyThrows
    protected void sendToClient(final TerminalRS terminalRS) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                terminalProcessListenerManager.listenerMap().forEach(
                        (key, value) -> value
                                .responseFromPty(terminalRS.getText().getBytes(StandardCharsets.UTF_8), webSocketSession));
            }
        }).start();

        // browser refresh and close session
        if (webSocketSession.isOpen()) {
            webSocketSession.sendMessage(terminalRS.toTextMessage());
        }
    }
}
