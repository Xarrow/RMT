package io.github.xarrow.rmt.api.session;

import io.github.xarrow.rmt.api.protocol.TagFilter;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTerminalSessionManager implements TerminalSessionManager {
    private final Map<String, SessionWrapper> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void registerSession(SessionWrapper sessionWrapper) {
        sessionMap.put(sessionWrapper.webSocketSession().getId(), sessionWrapper);
    }

    @Override
    public SessionWrapper getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public void removeSession(SessionWrapper sessionWrapper) {
        removeSession(sessionWrapper.webSocketSession().getId());
    }

    @Override
    public Map<String, SessionWrapper> sessionMap() {
        return sessionMap;
    }

    @Override
    public <T> void p2pSend(SessionWrapper SessionWrapper, TerminalMessage terminalMessage) throws IOException {
        if (!SessionWrapper.webSocketSession().isOpen()) {
            return;
        }
        SessionWrapper.webSocketSession().sendMessage(terminalMessage.webSocketMessage());
    }

    @Override
    public <T> void p2pSend(SessionWrapper SessionWrapper, WebSocketMessage<T> webSocketMessage) throws IOException {

    }

    @Override
    public <T> void broadCastSend(Map<String, SessionWrapper> sessionMap, WebSocketMessage<T> webSocketMessage) {
        sessionMap.forEach((key, value) -> {
            try {
                p2pSend(value, webSocketMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public <T> void filteredSend(TagFilter messageFilter, Map<String, SessionWrapper> sessionMap, WebSocketMessage<T> webSocketMessage) {


    }

    public void removeSession(final String sessionId) {
        sessionMap.remove(sessionId);
    }
}
