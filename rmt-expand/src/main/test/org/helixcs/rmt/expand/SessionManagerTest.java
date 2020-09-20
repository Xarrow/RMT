package org.helixcs.rmt.expand;

import org.helixcs.rmt.api.session.TerminalSessionManager;
import org.helixcs.rmt.expand.session.ConsulTerminalSessionManager;
import org.junit.Test;

public class SessionManagerTest {
    @Test
    public void testConsul() {
        TerminalSessionManager tm = new ConsulTerminalSessionManager();
    }

}
