package io.github.xarrow.rmt.api.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Writer;

import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.lifecycle.TerminalProcessLifecycle;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalProcessListener extends TerminalListenerOrder {
    // 获取监听器名称
    String listenerName();

    // 准备
    default void prepared(TerminalMessage message) { }

    // 初始化之前
    default void beforeInit(TerminalMessage message) {}

    // 已经完成初始化，只会执行一次，拿到当前 Session ,Process
    default void afterInit(final TerminalMessage message,
                           final WebSocketSession socketSession,
                           final PtyProcess ptyProcess,
                           final BufferedWriter stdout,
                           final BufferedReader stdin,
                           final BufferedReader stderr) {}

    // 发送命令之前，保留
    default void beforeCommand(final TerminalMessage message) {}

    // 发送process 原始报文
    default void requestToPty(final byte[] bytes, final Writer writer) {}

    // process 返回原始报文
    default void responseFromPty(final byte[] bytes, final WebSocketSession webSocketSession) {}

    // 发送命令之后，保留
    default void afterCommand(TerminalMessage message) {}

    // 关闭
    default void closed(TerminalMessage message) {}

    // 上下文，生命周期
    default void lifeCycleContext(final TerminalProcessLifecycle terminalProcessLifecycle) {}
}
