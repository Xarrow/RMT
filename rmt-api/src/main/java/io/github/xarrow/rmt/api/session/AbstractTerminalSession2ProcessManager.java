package io.github.xarrow.rmt.api.session;

import io.github.xarrow.rmt.api.protocol.TagFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTerminalSession2ProcessManager implements TerminalSession2ProcessManager {
    private final Map<Object, TerminalSession2ProcessMap> terminalSession2ProcessMappedMap = new ConcurrentHashMap<>();

    @Override
    public <T extends TagFilter> Map<Object, TerminalSession2ProcessMap> terminalSession2ProcessMappedMap() {
        return terminalSession2ProcessMappedMap;
    }

    @Override
    public <T extends TagFilter> void session2ProcessBind(T t, TerminalSession2ProcessMap terminalSession2ProcessMap) {
        terminalSession2ProcessMappedMap.put(t, terminalSession2ProcessMap);

    }

    @Override
    public <T extends TagFilter> void session2ProcessRelease(T t) {
        terminalSession2ProcessMappedMap.remove(t);

    }

    @Override
    public <T extends TagFilter> TerminalSession2ProcessMap getTerminalSession2ProcessMap(T t) {
        return terminalSession2ProcessMappedMap.get(t);
    }
}
