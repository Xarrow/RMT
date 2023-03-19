package io.github.xarrow.rmt.api.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionId ==> TerminalContext
 */
public abstract class AbstractTerminalContextManager implements TerminalContextManager {
    private final Map<String, TerminalContext> terminalContextMap = new ConcurrentHashMap<>();

    @Override
    public void registerTerminalContext(TerminalContext terminalContext) {
        String sessionId = terminalContext.session().getId();
        terminalContextMap.put(sessionId, terminalContext);
    }

    @Override
    public TerminalContext getTerminalContext(String sessionId) {
        return terminalContextMap.get(sessionId);
    }

    @Override
    public void removeTerminalContext(TerminalContext terminalContext) {
        removeSession(terminalContext.session().getId());
    }

    @Override
    public Map<String, TerminalContext> allTerminalContextMap() {
        return terminalContextMap;
    }

    public void removeSession(final String sessionId) {
        allTerminalContextMap().remove(sessionId);
    }
}
