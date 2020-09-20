package org.helixcs.rmt.api.lifecycle;

import org.helixcs.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
public interface TerminalProcessLifecycle {
    // connection and get session at first time
    void terminalConnection(WebSocketSession session);

    // ready
    void terminalReady(final TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // init
    void terminalInit(final TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // resize
    void terminalResize(final TerminalMessage terminalMessage) throws IOException;

    // command
    void terminalCommand(final TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // close
    void terminalClose(final TerminalMessage terminalMessage);

    @Deprecated
    void setWebSocketSession(WebSocketSession session);

    void terminalHeartbeat(final TerminalMessage terminalMessage);

}
