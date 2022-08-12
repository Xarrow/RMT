package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class HeartbeatBufferedReaderThread extends AbstractBufferedThread {

    @Override
    public void run() {
        TerminalRS ok = new TerminalRS();
        ok.setType(AbstractTerminalStructure.MessageType.TERMINAL_HEARTBEAT);
        ok.setText("ok");
        sendToClient(ok);
    }
}
