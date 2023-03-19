package io.github.xarrow.rmt.api.websocket;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.MessageFormat;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Slf4j
public class TerminalTextWebSocketHandler extends TextWebSocketHandler {
    private TerminalProcess terminalProcess;

    @Autowired
    public void setTerminalProcess(TerminalProcess terminalProcess) {
        this.terminalProcess = terminalProcess;
    }

    /**
     * 建立连接
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.debug(MessageFormat.format("[afterConnectionEstablished] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));
        // 建立连接
        terminalProcess.terminalConnection(session);
    }

    /**
     * 处理 Session ，按文本方式处理
     * TERMINAL_READY
     * TERMINAL_INIT
     * TERMINAL_RESIZE
     * TERMINAL_COMMAND
     * TERMINAL_CLOSE
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug(MessageFormat.format("[handleTextMessage] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));

        if (!session.isOpen()) {
            log.error(MessageFormat.format("[handleTextMessage] Session={0} isOpen={1}", session.getId(), session.isOpen()));
            return;
        }
        // dispatch
        // message to terminalRQ
        TerminalRQ terminalRQ = new TerminalRQ().toTerminalRQ(message);
        switch (terminalRQ.getType()) {
            case TERMINAL_READY:
                terminalProcess.terminalReady(session, terminalRQ);
                break;
            case TERMINAL_INIT:
                terminalProcess.terminalInit(session, terminalRQ);
                break;
            case TERMINAL_RESIZE:
                terminalProcess.terminalResize(session, terminalRQ);
                break;
            case TERMINAL_COMMAND:
                terminalProcess.terminalCommand(session, terminalRQ);
                break;
            case TERMINAL_HEARTBEAT:
                terminalProcess.terminalHeartbeat(session, terminalRQ);
                break;
            case TERMINAL_CLOSE:
                terminalProcess.terminalClose(session, terminalRQ);
                break;
            default:
                log.error(MessageFormat.format("[handleTextMessage] Session={0}  unknown message type", session.getId()));
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info(MessageFormat.format("[afterConnectionClosed] Session={0} closing ,isOpen={1}, status={2}", session.getId(), session.isOpen(), status));
        terminalProcess.terminalClose(session, null);
    }

}
