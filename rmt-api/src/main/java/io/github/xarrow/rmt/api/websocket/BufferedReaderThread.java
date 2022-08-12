package io.github.xarrow.rmt.api.websocket;

import java.io.BufferedReader;

import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;
import lombok.SneakyThrows;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/18/2020.
 * @Desc:
 */
public class BufferedReaderThread extends AbstractBufferedThread {

    protected AbstractTerminalStructure.MessageType messageType;

    public BufferedReaderThread setMessageType(AbstractTerminalStructure.MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    @Override
    public void run() {
        // send message to client
        printReader(bufferedReader);
    }

    @SneakyThrows
    private void printReader(BufferedReader bufferedReader) {
        int nRead;
        int defaultSendLength = 2 * 1024;
        char[] data = new char[defaultSendLength];
        while (/*bufferedReader.ready() &&*/ (nRead = bufferedReader.read(data, 0, data.length)) != -1) {
            TerminalRS terminalRS = new TerminalRS();
            terminalRS.setText(String.valueOf(data, 0, nRead));
            terminalRS.setType(this.messageType);
            sendToClient(terminalRS);
        }
    }

}
