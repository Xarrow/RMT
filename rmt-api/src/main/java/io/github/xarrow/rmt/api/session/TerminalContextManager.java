package io.github.xarrow.rmt.api.session;

import java.util.Map;

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
     * @param terminalContext
     */
    void registerTerminalContext(TerminalContext terminalContext);

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

}
