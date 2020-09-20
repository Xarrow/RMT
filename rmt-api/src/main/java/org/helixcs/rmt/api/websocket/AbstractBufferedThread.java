package org.helixcs.rmt.api.websocket;

import org.helixcs.rmt.api.listener.TerminalProcessListenerManager;
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
    protected TerminalProcessListenerManager manager;
    protected WebSocketSession webSocketSession;
    protected BufferedReader bufferedReader;
    protected BufferedWriter bufferedWriter;

    public AbstractBufferedThread setManager(TerminalProcessListenerManager manager) {
        this.manager = manager;
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

    protected void sendToClient(final TerminalRS terminalRS) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                manager.listenerMap().forEach(
                    (key, value) -> value
                        .responseFromPty(terminalRS.getText().getBytes(StandardCharsets.UTF_8), webSocketSession));
            }
        }).start();
        // browser refresh and close session
        if (webSocketSession.isOpen()) {
            try {
                webSocketSession.sendMessage(terminalRS.toTextMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
