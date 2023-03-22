package io.github.xarrow.rmt.app;

import io.github.xarrow.rmt.api.session.TerminalContextManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class RmtConfiguration implements CommandLineRunner {
    @Resource
    private TerminalContextManager terminalContextManager;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Thread.sleep(3000L);
            System.out.println("Map size==>" + terminalContextManager.allTerminalContextMap().size());
            System.out.println("Set size==>" + terminalContextManager.testSet().size());
            System.out.println("=======================");
        }

    }
}
