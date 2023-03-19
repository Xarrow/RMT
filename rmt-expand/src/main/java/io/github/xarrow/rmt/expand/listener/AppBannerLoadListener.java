package io.github.xarrow.rmt.expand.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import static io.github.xarrow.rmt.api.websocket.MessageType.TERMINAL_PRINT;


/**
 * @Author: helix
 * @Time:2022/8/11
 * @Site: https://github.com/xarrow
 */
public class AppBannerLoadListener implements TerminalProcessListener, EnvironmentAware, ApplicationContextAware {
    private ApplicationContext context;
    private WebSocketSession session;
    private Environment environment;

    @Override
    public void sessionConnect(WebSocketSession webSocketSession) {
        this.session = webSocketSession;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void beforeInit(TerminalMessage message) {
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
                session.sendMessage(textMessage);
            }
            String port = context.getEnvironment().getProperty("local.server.port");
            String webPath = context.getEnvironment().getProperty("rmt.starter.web-path");
            String tip = String.format("RMT started at:: http://127.0.0.1:%s/%s\n", port, webPath);
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                put("text", tip);
                put("type", TERMINAL_PRINT);
            }})));

            //env
            String env = String.format("Current env is::%s", environment.getProperty("env"));
            session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                put("text", env);
                put("type", TERMINAL_PRINT);
            }})));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
