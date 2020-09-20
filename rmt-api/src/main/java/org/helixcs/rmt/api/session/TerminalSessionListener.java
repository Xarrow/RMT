package org.helixcs.rmt.api.session;

import java.util.EventListener;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc: Session 变化监听
 */
public interface TerminalSessionListener extends EventListener {
    void doProcess(TerminalSessionChangeEvent event);
}
