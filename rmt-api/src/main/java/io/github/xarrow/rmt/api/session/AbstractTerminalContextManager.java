package io.github.xarrow.rmt.api.session;

import io.github.xarrow.rmt.api.protocol.TagFilter;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionId ==> TerminalContext
 */
public abstract class AbstractTerminalContextManager implements TerminalContextManager {
    private final Map<String, TerminalContext> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void registerSession(TerminalContext terminalContext) {
        sessionMap.put(terminalContext.webSocketSession().getId(), terminalContext);
    }

    @Override
    public TerminalContext getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public void removeSession(TerminalContext terminalContext) {
        removeSession(terminalContext.webSocketSession().getId());
    }

    @Override
    public Map<String, TerminalContext> sessionMap() {
        return sessionMap;
    }

    @Override
    public <T> void p2pSend(TerminalContext TerminalContext, TerminalMessage terminalMessage) throws IOException {
        if (!TerminalContext.webSocketSession().isOpen()) {
            return;
        }
        TerminalContext.webSocketSession().sendMessage(terminalMessage.webSocketMessage());
    }

    @Override
    public <T> void p2pSend(TerminalContext TerminalContext, WebSocketMessage<T> webSocketMessage) throws IOException {

    }

    @Override
    public <T> void broadCastSend(Map<String, TerminalContext> sessionMap, WebSocketMessage<T> webSocketMessage) {
        sessionMap.forEach((key, value) -> {
            try {
                p2pSend(value, webSocketMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public <T> void filteredSend(TagFilter messageFilter, Map<String, TerminalContext> sessionMap, WebSocketMessage<T> webSocketMessage) {


    }

    public void removeSession(final String sessionId) {
        sessionMap.remove(sessionId);
    }
}
