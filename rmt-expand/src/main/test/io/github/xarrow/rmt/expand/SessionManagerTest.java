package io.github.xarrow.rmt.expand;

import io.github.xarrow.rmt.api.session.TerminalSessionManager;
import io.github.xarrow.rmt.expand.session.ConsulTerminalSessionManager;
import org.junit.Test;

public class SessionManagerTest {
    @Test
    public void testConsul() {
        TerminalSessionManager tm = new ConsulTerminalSessionManager();
    }

}
