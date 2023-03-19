package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class RespondToWebBufferedThread extends AbstractBufferedThread {
    protected BufferedReader bufferedReader;
    protected AbstractTerminalStructure.MessageType messageType;
    private WebSocketSession session;


    public RespondToWebBufferedThread(BufferedReader bufferedReader,
                                      AbstractTerminalStructure.MessageType messageType,
                                      WebSocketSession session) {
        this.bufferedReader = bufferedReader;
        this.messageType = messageType;
        this.session = session;
    }

    public RespondToWebBufferedThread(BufferedReader bufferedReader,
                                      WebSocketSession session) {
        this.bufferedReader = bufferedReader;
        this.messageType = TERMINAL_PRINT;
        this.session = session;
    }

    @Override
    public void run() {
        // send message to client
        printReader(bufferedReader);
    }


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
        if (session.isOpen()) {
            session.sendMessage(terminalRS.toTextMessage());
        }
    }

    @SneakyThrows
    private void printReader(BufferedReader bufferedReader) {
        int nRead;
        int defaultSendLength = 2 * 1024;
        char[] data = new char[defaultSendLength];
        while (/*bufferedReader.ready() &&*/ (nRead = bufferedReader.read(data, 0, data.length)) != -1) {
            TerminalRS terminalRS = new TerminalRS();
            terminalRS.setText(String.valueOf(data, 0, nRead));
            terminalRS.setType(this.messageType);
            sendToClient(terminalRS);
        }
    }
}
