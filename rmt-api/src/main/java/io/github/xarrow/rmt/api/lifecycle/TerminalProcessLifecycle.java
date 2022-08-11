package io.github.xarrow.rmt.api.lifecycle;

import io.github.xarrow.rmt.api.protocol.TerminalMessage;
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
    void terminalReady(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // init
    void terminalInit(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // resize
    void terminalResize(WebSocketSession session, TerminalMessage terminalMessage) throws IOException;

    // command
    void terminalCommand(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException;

    // close
    void terminalClose(WebSocketSession session, TerminalMessage terminalMessage);

    void terminalHeartbeat(WebSocketSession session, TerminalMessage terminalMessage);

}
