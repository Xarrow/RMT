package io.github.xarrow.rmt.expand.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pty4j.PtyProcess;
import org.apache.commons.io.IOUtils;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;

/**
 * @Email: zhangjian12424@gmail.com .
 * @Author: helixcs
 * @Date: 6/23/2020.
 * @Desc: 启动加载 banner
 */
public class AppStartBannerLoadListener implements TerminalProcessListener {
    private static final String NEXT_SYMBOL = "\u001B[?25l\n";

    @Override
    public String listenerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void afterInit(TerminalMessage message, WebSocketSession socketSession,
                          PtyProcess ptyProcess, BufferedWriter stdout, BufferedReader stdin,
                          BufferedReader stderr) {
        try {
            org.springframework.core.io.Resource resource = new ClassPathResource("rmt.banner");
            List<String> strings = IOUtils.readLines(resource.getInputStream(), "utf-8");

            for (int i = 0; i < strings.size(); i++) {
                int finalI = i;
                HashMap<String, Object> hashMap = new HashMap<String, Object>() {
                    {
                        if (finalI == strings.size() - 1) {
                            put("text", MessageFormat
                                .format("\u001B[?25l\n {0} \u001B[?25l\n\u001B[?25l\n", strings.get(finalI)));
                        } else {
                            put("text", MessageFormat.format("\u001B[?25l\n {0} ", strings.get(finalI)));
                        }
                        put("type", TERMINAL_PRINT);
                    }
                };
                TextMessage textMessage = new TextMessage(new ObjectMapper().writeValueAsString(hashMap));
                socketSession.sendMessage(textMessage);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
