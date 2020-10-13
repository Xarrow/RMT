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
    // 注册session
    void registerSession(final SessionWrapper SessionWrapper);

    // 移除session
    void removeSession(final SessionWrapper SessionWrapper);

    SessionWrapper getSession(final String sessionId);

    // 全部session 集合
    Map<String, SessionWrapper> sessionMap();

    // 点对点发送
    <T> void p2pSend(final SessionWrapper SessionWrapper, final TerminalMessage terminalMessage) throws IOException;

    @Deprecated
    <T> void p2pSend(final SessionWrapper SessionWrapper, final WebSocketMessage<T> webSocketMessage) throws IOException;

    // 广播发送
    <T> void broadCastSend(final Map<String, SessionWrapper> sessionMap, final WebSocketMessage<T> webSocketMessage);

    // 过滤发送
    // todo
    <T> void filteredSend(final TagFilter messageFilter, final Map<String, SessionWrapper> sessionMap, final WebSocketMessage<T> webSocketMessage);

    // Session 变动监听
    default void addListener(TerminalSessionListener terminalSessionListener) {

    }

}
