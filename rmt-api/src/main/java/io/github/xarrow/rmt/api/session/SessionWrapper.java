package io.github.xarrow.rmt.api.session;

import org.springframework.web.socket.WebSocketSession;

public interface SessionWrapper {
    WebSocketSession webSocketSession();
}
