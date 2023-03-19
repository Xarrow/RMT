package io.github.xarrow.rmt.api.session;

import java.io.IOException;
import java.util.Map;

import io.github.xarrow.rmt.api.protocol.TagFilter;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketMessage;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalContextManager {

    /**
     * register sessionContext
     *
     * @param terminalContext
     */
    void registerTerminalContext(TerminalContext terminalContext);

    /**
     * remove session
     *
     * @param TerminalContext
     */
    void removeSession(TerminalContext TerminalContext);

    /**
     * get terminalContext by sessionId
     *
     * @param sessionId
     * @return
     */
    TerminalContext getTerminalContext(String sessionId);

    /**
     * all sessions
     *
     * @return
     */
    Map<String, TerminalContext> allTerminalContextMap();

    /**
     * p2p send
     *
     * @param TerminalContext
     * @param terminalMessage
     * @param <T>
     * @throws IOException
     */
    <T> void p2pSend(TerminalContext TerminalContext, TerminalMessage terminalMessage) throws IOException;

    @Deprecated
    <T> void p2pSend(TerminalContext TerminalContext, WebSocketMessage<T> webSocketMessage)
            throws IOException;

    /**
     * broadcast
     *
     * @param sessionMap
     * @param webSocketMessage
     * @param <T>
     */
    <T> void broadCastSend(Map<String, TerminalContext> sessionMap, WebSocketMessage<T> webSocketMessage);

    /**
     * filter send
     *
     * @param messageFilter
     * @param sessionMap
     * @param webSocketMessage
     * @param <T>
     */
    <T> void filteredSend(TagFilter messageFilter,
                          Map<String, TerminalContext> sessionMap,
                          WebSocketMessage<T> webSocketMessage);

    /**
     * listener session
     *
     * @param terminalSessionListener
     */
    default void addListener(TerminalSessionListener terminalSessionListener) {

    }

}
