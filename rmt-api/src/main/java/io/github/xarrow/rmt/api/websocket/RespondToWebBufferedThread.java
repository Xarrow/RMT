package io.github.xarrow.rmt.api.websocket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;

import static io.github.xarrow.rmt.api.websocket.MessageType.TERMINAL_PRINT;


/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
@Slf4j
public class RespondToWebBufferedThread extends AbstractBufferedThread {
    protected BufferedReader bufferedReader;
    protected MessageType messageType;
    private final WebSocketSession session;
    private TerminalProcessExtend terminalProcessExtend;


    public RespondToWebBufferedThread(BufferedReader bufferedReader,
                                      MessageType messageType,
                                      WebSocketSession session,
                                      TerminalProcessExtend terminalProcessExtend) {
        this.bufferedReader = bufferedReader;
        this.messageType = messageType;
        this.session = session;
        this.terminalProcessExtend = terminalProcessExtend;
    }

    public RespondToWebBufferedThread(BufferedReader bufferedReader,
                                      WebSocketSession session,
                                      TerminalProcessExtend terminalProcessExtend) {
        this.bufferedReader = bufferedReader;
        this.messageType = TERMINAL_PRINT;
        this.session = session;
        this.terminalProcessExtend = terminalProcessExtend;
    }

    public RespondToWebBufferedThread(BufferedReader bufferedReader,
                                      MessageType messageType,
                                      WebSocketSession session) {
        this.bufferedReader = bufferedReader;
        this.messageType = messageType;
        this.session = session;
    }

    @Override
    public void run() {
        printReader(bufferedReader);
    }

    @SneakyThrows
    private void printReader(BufferedReader bufferedReader) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            int nRead;
            int defaultSendLength = 2 * 1024;
            char[] data = new char[defaultSendLength];
            while (/*bufferedReader.ready() &&*/ (nRead = bufferedReader.read(data, 0, data.length)) != -1) {
                String result = String.valueOf(data, 0, nRead);
                resultStringBuilder.append(result);

                TerminalRS terminalRS = new TerminalRS();
                terminalRS.setText(result);
                terminalRS.setType(messageType);
                TextMessage textMessage = terminalRS.toTextMessage();
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("");
        } finally {
            TerminalRS terminalRS = new TerminalRS();
            terminalRS.setText(resultStringBuilder.toString());
            terminalRS.setType(messageType);
            // 6. after command
            terminalProcessExtend.doAfterCommandListener(terminalRS);
        }
    }

    public void sendToClient(TerminalRS terminalRS) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(terminalRS.toTextMessage());
        }
    }
}
