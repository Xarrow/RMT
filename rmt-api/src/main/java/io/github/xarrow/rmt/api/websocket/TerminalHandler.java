package io.github.xarrow.rmt.api.websocket;

import java.text.MessageFormat;

import io.github.xarrow.rmt.api.lifecycle.TerminalProcessLifecycle;
import io.github.xarrow.rmt.api.session.SessionWrapper;
import lombok.extern.slf4j.Slf4j;
import io.github.xarrow.rmt.api.session.TerminalSessionManager;
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

    /**
     * @param terminalProcessLifecycle
     * @See io.github.xarrow.rmt.spring.boot.starter.autoconfigure.RmtConfiguration#terminalProcessLifecycle()
     */
    @Autowired
    public void setTerminalProcessLifecycle(TerminalProcessLifecycle terminalProcessLifecycle) {
        this.terminalProcessLifecycle = terminalProcessLifecycle;
    }

    /**
     * @param terminalSessionManager
     * @See io.github.xarrow.rmt.spring.boot.starter.autoconfigure.RmtConfiguration#terminalSessionManager()
     */
    @Autowired
    public void setTerminalSessionManager(TerminalSessionManager terminalSessionManager) {
        this.terminalSessionManager = terminalSessionManager;
    }

    /**
     * 建立连接
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.debug(MessageFormat
                .format("[afterConnectionEstablished] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));
        //terminalProcessLifecycle.setWebSocketSession(session);
        // 建立连接
        terminalProcessLifecycle.terminalConnection(session);
        // 注册连接
        terminalSessionManager.registerSession(new SessionWrapper() {
            @Override
            public WebSocketSession webSocketSession() {
                return session;
            }
        });
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
        log.debug(MessageFormat
                .format("[handleTextMessage] Session={0} closing ,isOpen={1}", session.getId(), session.isOpen()));

        if (!session.isOpen()) {
            log.error(MessageFormat
                    .format("[handleTextMessage] Session={0} isOpen={1}", session.getId(), session.isOpen()));
            return;
        }
        // 请求
        TerminalRQ terminalRQ = new TerminalRQ().toTerminalRQ(message);
        switch (terminalRQ.getType()) {
            case TERMINAL_READY:
                terminalProcessLifecycle.terminalReady(session, terminalRQ);
                break;
            case TERMINAL_INIT:
                terminalProcessLifecycle.terminalInit(session, terminalRQ);
                break;
            case TERMINAL_RESIZE:
                terminalProcessLifecycle.terminalResize(session, terminalRQ);
                break;
            case TERMINAL_COMMAND:
                terminalProcessLifecycle.terminalCommand(session, terminalRQ);
                break;
            case TERMINAL_HEARTBEAT:
                terminalProcessLifecycle.terminalHeartbeat(session, terminalRQ);
                break;
            case TERMINAL_CLOSE:
                terminalProcessLifecycle.terminalClose(session, terminalRQ);
                break;
            default:
                log.error(MessageFormat
                        .format("[handleTextMessage] Session={0}  unknown message type", session.getId()));
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
        terminalProcessLifecycle.terminalClose(session, null);
        session.close();
        terminalSessionManager.removeSession(new SessionWrapper() {
            @Override
            public WebSocketSession webSocketSession() {
                return session;
            }
        });
    }

}
