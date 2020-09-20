package org.helixcs.rmt.api.session;

import org.springframework.web.socket.WebSocketSession;

public interface SessionWrapper {
    WebSocketSession webSocketSession();
}
