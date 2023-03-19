package io.github.xarrow.rmt.api.session;

import org.springframework.web.socket.WebSocketSession;

/**
 * bind session and process
 */
public interface TerminalContext {
    WebSocketSession session();

    ProcessWrapper processWrapper();
}
