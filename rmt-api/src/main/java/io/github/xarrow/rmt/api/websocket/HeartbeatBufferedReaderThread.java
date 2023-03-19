package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class HeartbeatBufferedReaderThread extends RespondToWebBufferedThread {
    public HeartbeatBufferedReaderThread(BufferedReader bufferedReader,
                                         WebSocketSession session) {
        super(bufferedReader, MessageType.TERMINAL_HEARTBEAT, session);
    }

    @Override
    public void run() {
        TerminalRS ok = new TerminalRS();
        ok.setType(this.messageType);
        ok.setText("ok");
        try {
            sendToClient(ok);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
