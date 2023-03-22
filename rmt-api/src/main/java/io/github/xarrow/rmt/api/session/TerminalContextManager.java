package io.github.xarrow.rmt.api.session;

import java.util.Map;
import java.util.Set;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public interface TerminalContextManager {

    /**
     * register sessionContext
     *
     * @param terminalContexts
     */
    void registerTerminalContext(TerminalContext... terminalContexts);

    /**
     * build terminalContext as map
     */
    void buildTerminalContext();

    /**
     * remove session
     *
     * @param TerminalContext
     */
    void removeTerminalContext(TerminalContext TerminalContext);

    /**
     * get terminalContext by sessionId
     *
     * @param sessionId
     * @return
     */
    TerminalContext getTerminalContext(String sessionId);

    /**
     * all sessions
     *
     * @return
     */
    Map<String, TerminalContext> allTerminalContextMap();

    @Deprecated
    Set<TerminalContext> testSet();

}
