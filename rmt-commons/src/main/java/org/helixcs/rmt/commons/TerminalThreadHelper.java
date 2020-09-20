package org.helixcs.rmt.commons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class TerminalThreadHelper {
    static class TerminalThreadFactory implements ThreadFactory {
        private String threadName;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "RMT-thread-" + this.threadName);
        }

        public TerminalThreadFactory setThreadName(String threadName) {
            this.threadName = threadName;
            return this;
        }
    }

    public static ExecutorService readerHandlerThreadPool = Executors.newFixedThreadPool(3,
        new TerminalThreadFactory().setThreadName("stdout"));
    public static ExecutorService errorHandlerThreadPool = Executors.newFixedThreadPool(3,
        new TerminalThreadFactory().setThreadName("stderr"));
    public static ExecutorService writeHandlerThreadPool = Executors.newFixedThreadPool(3,
        new TerminalThreadFactory().setThreadName("stdin"));
    public static ExecutorService heartbeatHandlerThreadPool = Executors.newFixedThreadPool(1,
        new TerminalThreadFactory().setThreadName("heartbeat"));
}
