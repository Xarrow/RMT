package io.github.xarrow.rmt.expand.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pty4j.PtyProcess;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;

/**
 * @Author: helix
 * @Time:2022/8/11
 * @Site: https://github.com/xarrow
 */
public class AppBannerLoadListener implements TerminalProcessListener, EnvironmentAware {

    private ApplicationContext context;

    private Environment environment;

    public void setSpringApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public String listenerName() {
        return AppBannerLoadListener.class.getName();
    }

    @Override
    public void afterInit(TerminalMessage message, WebSocketSession socketSession, PtyProcess ptyProcess, BufferedWriter stdout, BufferedReader stdin, BufferedReader stderr) {
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
            String tip = String.format("RMT started at:: http://127.0.0.1:%s/%s\n", port, webPath);
            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                put("text", tip);
                put("type", TERMINAL_PRINT);
            }})));

            //env
            String env = String.format("Current env is::%s", environment.getProperty("env"));
            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                put("text", env);
                put("type", TERMINAL_PRINT);
            }})));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
