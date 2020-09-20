package org.helixcs.rmt.api.protocol;

import org.springframework.web.socket.WebSocketMessage;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalMessage {
    default <T> WebSocketMessage<T> webSocketMessage() {
        return null;
    }
}
