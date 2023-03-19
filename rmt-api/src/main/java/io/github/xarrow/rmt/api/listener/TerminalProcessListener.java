package io.github.xarrow.rmt.api.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Writer;

import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalProcessListener extends TerminalListenerOrder {
    /**
     * ListenerName
     *
     * @return
     */
    default String listenerName() {
        return this.getClass().getSimpleName();
    }

    ;

    /**
     * websocket session connect
     * only notify once
     *
     * @param webSocketSession
     */
    default void sessionConnect(WebSocketSession webSocketSession) {
    }

    // todo delete 准备
    default void prepared(TerminalMessage message) {
    }

    // 初始化之前
    default void beforeInit(TerminalMessage message) {
    }

    /**
     * when after init, get Process
     *
     * @param message
     * @param process
     */
    default void afterInit(TerminalMessage message, Process process) {
    }

    /**
     * before command execute, and before push Queue
     *
     * @param message
     */
    default void beforeCommand(TerminalMessage message) {
    }

    /**
     * 发送process 原始报文
     *
     * @param bytes
     * @param writer
     */
    default void requestToPty(final byte[] bytes, final Writer writer) {
    }

    /**
     * process 返回原始报文
     *
     * @param bytes
     * @param webSocketSession
     */
    default void responseFromPty(final byte[] bytes, final WebSocketSession webSocketSession) {
    }

    // 发送命令之后，保留
    default void afterCommand(TerminalMessage message) {
    }

    // 关闭
    default void closed(TerminalMessage message) {
    }

    // 上下文，生命周期
    default void lifeCycleContext(final TerminalProcess terminalProcess) {
    }
}
