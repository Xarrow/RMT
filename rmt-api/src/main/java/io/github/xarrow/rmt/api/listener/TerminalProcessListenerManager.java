package io.github.xarrow.rmt.api.listener;

import java.util.Map;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/19/2020.
 * @Desc:
 */
public interface TerminalProcessListenerManager {
    void registerListener(final TerminalProcessListener listener);

    void removeListener(final TerminalProcessListener listener);

    TerminalProcessListener findListener(final String name);

    Map<String, TerminalProcessListener> allListenerMap();


}
