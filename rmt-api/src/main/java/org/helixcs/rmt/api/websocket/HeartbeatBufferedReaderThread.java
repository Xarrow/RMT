package org.helixcs.rmt.api.websocket;

import org.helixcs.rmt.api.protocol.AbstractTerminalStructure;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class HeartbeatBufferedReaderThread extends AbstractBufferedThread {
    //webSocketSession
    //TerminalProcessListenerManager

    @Override
    public void run() {
        TerminalRS ok = new TerminalRS();
        ok.setType(AbstractTerminalStructure.MessageType.TERMINAL_HEARTBEAT);
        ok.setText("ok");
        sendToClient(ok);
    }
}
