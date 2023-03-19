package io.github.xarrow.rmt.api.lifecycle;

import io.github.xarrow.rmt.api.exception.TerminalProcessException;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
public interface TerminalProcess {
    // connection and get session at first time
    void terminalConnection(WebSocketSession session);

    // ready
    void terminalReady(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException, TerminalProcessException;

    // init
    void terminalInit(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException, TerminalProcessException;

    // resize
    void terminalResize(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, TerminalProcessException;

    // command
    void terminalCommand(WebSocketSession session, TerminalMessage terminalMessage) throws IOException, InterruptedException, TerminalProcessException;

    // close
    void terminalClose(WebSocketSession session, TerminalMessage terminalMessage);

    // heartbeat
    void terminalHeartbeat(WebSocketSession session, TerminalMessage terminalMessage);

}
