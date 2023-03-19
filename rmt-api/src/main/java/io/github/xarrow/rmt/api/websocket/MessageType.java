package io.github.xarrow.rmt.api.websocket;

public enum MessageType {
    TERMINAL_READY,
    TERMINAL_INIT,
    TERMINAL_RESIZE,
    TERMINAL_COMMAND,
    TERMINAL_CLOSE,
    TERMINAL_PRINT,
    TERMINAL_HEARTBEAT;
}
