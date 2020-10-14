package io.github.xarrow.rmt.expand.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import lombok.SneakyThrows;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 10/14/2020.
 * @Desc:
 */
public class EmbeddedPythonEnvLoadListener implements TerminalProcessListener {
    @Override
    public String listenerName() {
        return EmbeddedPythonEnvLoadListener.class.getSimpleName();
    }

    @SneakyThrows
    @Override
    public void afterInit(TerminalMessage message, WebSocketSession socketSession,
                          PtyProcess ptyProcess, BufferedWriter stdout, BufferedReader stdin,
                          BufferedReader stderr) {

        //stdout.write("ping baidu.com \r");
        Map<String, Object> hashMap = new HashMap<String, Object>() {{
            put("type", TERMINAL_PRINT);
            put("text", listenerName() + "Test OK");
        }};
        TextMessage textMessage = new TextMessage(new ObjectMapper().writeValueAsString(hashMap));
        socketSession.sendMessage(textMessage);

    }
}
