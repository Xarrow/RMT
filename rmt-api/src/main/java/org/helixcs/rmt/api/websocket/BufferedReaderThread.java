package org.helixcs.rmt.api.websocket;

import java.io.BufferedReader;

import org.helixcs.rmt.api.protocol.AbstractTerminalStructure;

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

    private void printReader(BufferedReader bufferedReader) {
        try {
            int nRead;
            int defaultSendLength = 2 * 1024;
            char[] data = new char[defaultSendLength];
            while ((nRead = bufferedReader.read(data, 0, data.length)) != -1) {
                TerminalRS terminalRS = new TerminalRS();
                terminalRS.setText(String.valueOf(data, 0, nRead));
                terminalRS.setType(this.messageType);
                sendToClient(terminalRS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
