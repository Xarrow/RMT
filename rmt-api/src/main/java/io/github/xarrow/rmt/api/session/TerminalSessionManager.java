package io.github.xarrow.rmt.api.session;

import java.io.IOException;
import java.util.Map;

import io.github.xarrow.rmt.api.protocol.TagFilter;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketMessage;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalSessionManager {
    
    /**
     * register session
     *
     * @param SessionWrapper
     */
    void registerSession(final SessionWrapper SessionWrapper);
    
    /**
     * remove session
     *
     * @param SessionWrapper
     */
    void removeSession(final SessionWrapper SessionWrapper);
    
    SessionWrapper getSession(final String sessionId);
    
    /**
     * all sessions
     *
     * @return
     */
    Map<String, SessionWrapper> sessionMap();
    
    /**
     * p2p send
     *
     * @param SessionWrapper
     * @param terminalMessage
     * @param <T>
     * @throws IOException
     */
    <T> void p2pSend(final SessionWrapper SessionWrapper, final TerminalMessage terminalMessage) throws IOException;
    
    @Deprecated
    <T> void p2pSend(final SessionWrapper SessionWrapper, final WebSocketMessage<T> webSocketMessage)
            throws IOException;
    
    /**
     * broadcast
     *
     * @param sessionMap
     * @param webSocketMessage
     * @param <T>
     */
    <T> void broadCastSend(final Map<String, SessionWrapper> sessionMap, final WebSocketMessage<T> webSocketMessage);
    
    /**
     * filter send
     *
     * @param messageFilter
     * @param sessionMap
     * @param webSocketMessage
     * @param <T>
     */
    <T> void filteredSend(final TagFilter messageFilter, final Map<String, SessionWrapper> sessionMap,
            final WebSocketMessage<T> webSocketMessage);
    
    /**
     * listener session
     *
     * @param terminalSessionListener
     */
    default void addListener(TerminalSessionListener terminalSessionListener) {
    
    }
    
}
