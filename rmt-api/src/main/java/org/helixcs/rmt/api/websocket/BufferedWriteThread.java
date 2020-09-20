package org.helixcs.rmt.api.websocket;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class BufferedWriteThread extends AbstractBufferedThread {
    private String command;

    public BufferedWriteThread setCommand(String command) {
        this.command = command;
        return this;
    }

    @SneakyThrows
    @Override
    public void run() {
        writeToPty();
    }

    private void writeToPty() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                manager.listenerMap().values().forEach(
                    x -> x.requestToPty(command.getBytes(StandardCharsets.UTF_8), bufferedWriter));
            }
        }).start();
        bufferedWriter.write(command);
        bufferedWriter.flush();
    }
}
