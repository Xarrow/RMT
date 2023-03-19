package io.github.xarrow.rmt.api.session;

import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;

import java.util.Map;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class TerminalContextManagerListener implements TerminalProcessListener {
    @Override
    public void afterInit(TerminalMessage message, Process process) {
        TerminalProcessListener.super.afterInit(message, process);
    }
}
