package io.github.xarrow.rmt.api;

import com.pty4j.PtyProcess;
import com.pty4j.unix.Pty;
import com.sun.jna.Platform;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author: helix
 * @Time:2022/8/12
 * @Site: https://github.com/xarrow/
 */
@Slf4j
public class PtyProcessPool {
    private int initializeSize;
    private final LinkedList<PtyProcess> pool = new LinkedList<>();

    public PtyProcessPool(int initializeSize) {
        this.initializeSize = initializeSize;
        if (this.initializeSize > 0) {
            for (int i = 0; i < this.initializeSize; i++) {
                try {
                    PtyProcess ptyProcess = fetchInitializedPty();
                    pool.addLast(ptyProcess);
                } catch (IOException e) {
                    log.error(MessageFormat.format("PtyProcessPool init ptyProcess exception, {}", e));
                }
            }
        }
    }

    @SneakyThrows
    public PtyProcess fetch(long timeout) {
        synchronized (pool) {
            if (timeout <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + timeout;
                long remaining = timeout;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                PtyProcess ptyProcess = null;
                if (!pool.isEmpty()) {
                    ptyProcess = pool.removeFirst();
                }
                return ptyProcess;
            }
        }

    }

    public void release(PtyProcess ptyProcess) {
        if (ptyProcess != null && ptyProcess.isAlive()) {
            synchronized (pool) {
                pool.addLast(ptyProcess);
                pool.notifyAll();
            }
        }

    }

    private static PtyProcess fetchInitializedPty() throws IOException {
        // directory
        String userHome = System.getProperty("user.home");
        // cmd
        String[] command;
        // windows
        if (Platform.isWindows()) {
            command = "cmd.exe".split("\\s+");
        }
        // linux
        else {
            command = "/bin/bash -i".split("\\s+");
        }
        Map<String, String> envs = new HashMap<String, String>(System.getenv()) {{
            put("TERM", "xterm");
        }};
        return PtyProcess.exec(command, envs, userHome);
    }

}
