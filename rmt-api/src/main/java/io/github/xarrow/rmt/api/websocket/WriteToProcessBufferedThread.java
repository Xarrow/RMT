package io.github.xarrow.rmt.api.websocket;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class WriteToProcessBufferedThread extends AbstractBufferedThread {
    protected BufferedWriter bufferedWriter;
    private String command;

    public WriteToProcessBufferedThread(BufferedWriter bufferedWriter, String command) {
        this.bufferedWriter = bufferedWriter;
        this.command = command;
    }

    @SneakyThrows
    @Override
    public void run() {
        writeToPty();
    }

    private void writeToPty() throws IOException {
        bufferedWriter.write(command);
        bufferedWriter.flush();
    }
}
