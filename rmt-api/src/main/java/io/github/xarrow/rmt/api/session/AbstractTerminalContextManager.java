package io.github.xarrow.rmt.api.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SessionId ==> TerminalContext
 */
public abstract class AbstractTerminalContextManager implements TerminalContextManager {
    // when app start , custom terminalContext is registered
    private final Set<TerminalContext> terminalContextSet = new HashSet<>();
    // echo session has one TerminalContext
    private final Map<String, TerminalContext> terminalContextMap = new ConcurrentHashMap<>();


    @Override
    public void registerTerminalContext(TerminalContext... terminalContexts) {
        Collections.addAll(terminalContextSet, terminalContexts);
    }

    @Override
    public void buildTerminalContext() {
        if (CollectionUtils.isEmpty(terminalContextSet)) {
            return;
        }

        for (TerminalContext terminalContext : terminalContextSet) {
            WebSocketSession session = terminalContext.session();
            if (null == session) {
                continue;
            }
            String sessionId = session.getId();
            terminalContextMap.put(sessionId, terminalContext);
        }
    }

    @Override
    public TerminalContext getTerminalContext(String sessionId) {
        return terminalContextMap.get(sessionId);
    }

    @Override
    public void removeTerminalContext(TerminalContext terminalContext) {
        // no terminalContext implement
        if (null == terminalContext) {
            return;
        }
        WebSocketSession session = terminalContext.session();
        if (null == session) {
            return;
        }
        removeSession(session.getId());
    }

    @Override
    public Map<String, TerminalContext> allTerminalContextMap() {
        return terminalContextMap;
    }

    @Override
    public Set<TerminalContext> testSet() {
        return terminalContextSet;
    }

    public void removeSession(final String sessionId) {
        TerminalContext terminalContext = allTerminalContextMap().get(sessionId);
        if (null == terminalContext) {
            return;
        }
        allTerminalContextMap().remove(sessionId);
    }
}
