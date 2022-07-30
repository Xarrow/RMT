package io.github.xarrow.rmt.expand.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pty4j.PtyProcess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
@Slf4j
public class AppStartBannerLoadListener implements TerminalProcessListener {
    private static final String NEXT_SYMBOL = "\u001B[?25l\n";
    private ApplicationContext context;

    public void setSpringApplicationContext(ApplicationContext context) {
        this.context = context;
    }

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
                String text = MessageFormat.format("\u001B[?25l\n {0} ", strings.get(i));
                if (i == strings.size() - 1) {
                    text = MessageFormat
                            .format("\u001B[?25l\n {0} \u001B[?25l\n\u001B[?25l\n", strings.get(i));
                }
                String finalText = text;
                HashMap<String, Object> hashMap = new HashMap<String, Object>() {
                    {
                        put("text", finalText);
                        put("type", TERMINAL_PRINT);
                    }
                };
                TextMessage textMessage = new TextMessage(new ObjectMapper().writeValueAsString(hashMap));
                socketSession.sendMessage(textMessage);
            }
            String port = context.getEnvironment().getProperty("local.server.port");
            String webPath = context.getEnvironment().getProperty("rmt.starter.web-path");
            String tip = String.format("RMT started at:: http://127.0.0.1:%s/%s", port, webPath);
            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                put("text", tip);
                put("type", TERMINAL_PRINT);
            }})));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
