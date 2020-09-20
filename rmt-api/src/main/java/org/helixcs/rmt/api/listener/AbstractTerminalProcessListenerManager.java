package org.helixcs.rmt.api.listener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/19/2020.
 * @Desc:
 */
public abstract class AbstractTerminalProcessListenerManager implements TerminalProcessListenerManager {
    private final Map<String, TerminalProcessListener> listenerMap = new LinkedHashMap<>();

    public void registerListener(final TerminalProcessListener listener) {
        //default
        if (listener.getOrder() == 0) {
            listenerMap.put(listener.listenerName(), listener);
        }
    }

    public void removeListener(final TerminalProcessListener listener) {
        removeListener(listener.listenerName());
    }

    public void removeListener(final String name) {
        listenerMap.remove(name);
    }

    @Override
    public void sort() {
        // do thing
    }

    @Override
    public TerminalProcessListener findListener(String name) {
        return listenerMap.get(name);
    }

    public Map<String, TerminalProcessListener> listenerMap() {
        return listenerMap;
    }
}
