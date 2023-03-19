package io.github.xarrow.rmt.api.websocket;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class AbstractBufferedReaderThread extends AbstractBufferedThread {
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
