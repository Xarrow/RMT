package org.helixcs.rmt.api.websocket;

import java.text.MessageFormat;

import lombok.extern.slf4j.Slf4j;
import org.helixcs.rmt.api.lifecycle.TerminalProcessLifecycle;
import org.helixcs.rmt.api.session.SessionWrapper;
import org.helixcs.rmt.api.session.TerminalSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */
@Slf4j
public class TerminalHandler extends TextWebSocketHandler {
    private TerminalProcessLifecycle terminalProcessLifecycle;
    private TerminalSessionManager terminalSessionManager;

    @Autowired
    public void setTerminalProcessLifecycle(TerminalProcessLifecycle terminalProcessLifecycle) {
        this.terminalProcessLifecycle = terminalProcessLifecycle;
    }

    @Autowired
    public void setTerminalSessionManager(TerminalSessionManager terminalSessionManager) {
        this.terminalSessionManager = terminalSessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info(MessageFormat
            .format("[afterConnectionEstablished] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));
        //terminalProcessLifecycle.setWebSocketSession(session);
        terminalProcessLifecycle.terminalConnection(session);
        terminalSessionManager.registerSession(new SessionWrapper() {
            @Override
            public WebSocketSession webSocketSession() {
                return session;
            }
        });
    }

    // TERMINAL_READY
    // TERMINAL_INIT
    // TERMINAL_RESIZE
    // TERMINAL_COMMAND
    // TERMINAL_CLOSE
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(MessageFormat
            .format("[handleTextMessage] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));

        if (!session.isOpen()) {
            return;
        }
        TerminalRQ terminalRQ = new TerminalRQ().toTerminalRQ(message);
        switch (terminalRQ.getType()) {
            case TERMINAL_READY:
                terminalProcessLifecycle.terminalReady(terminalRQ);
                break;
            case TERMINAL_INIT:
                terminalProcessLifecycle.terminalInit(terminalRQ);
                break;
            case TERMINAL_RESIZE:
                terminalProcessLifecycle.terminalResize(terminalRQ);
                break;
            case TERMINAL_COMMAND:
                terminalProcessLifecycle.terminalCommand(terminalRQ);
                break;
            case TERMINAL_HEARTBEAT:
                terminalProcessLifecycle.terminalHeartbeat(terminalRQ);
                break;
            case TERMINAL_CLOSE:
                terminalProcessLifecycle.terminalClose(terminalRQ);
                break;
            default:
                break;
        }

    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // do nothing
        log.info(MessageFormat
            .format("[afterConnectionClosed] Session={0} closing ,isOpen={1}, status={2}", session.getId(),
                session.isOpen(), status));
        terminalProcessLifecycle.terminalClose(null);
        session.close();
        terminalSessionManager.removeSession(new SessionWrapper() {
            @Override
            public WebSocketSession webSocketSession() {
                return session;
            }
        });
    }

}
