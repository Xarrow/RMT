package org.helixcs.rmt.examples.session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.helixcs.rmt.api.protocol.AbstractTerminalStructure;
import org.helixcs.rmt.api.protocol.TerminalMessage;
import org.helixcs.rmt.api.session.SessionWrapper;
import org.helixcs.rmt.api.session.TerminalSession2ProcessManager;
import org.helixcs.rmt.api.session.TerminalSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.helixcs.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/17/2020.
 * @Desc:
 */

@Controller
@RequestMapping("examples")
public class SessionExamplesController {
    @RequestMapping("/")
    public String ops() {
        return "dev.html";
    }

    @Autowired
    private TerminalSessionManager terminalSessionManager;

    @RequestMapping("/api/session/showAllSession")
    @ResponseBody
    public DevApiRS showSession() {
        return new DevApiRS().setDevWsSessionMap(
                terminalSessionManager.sessionMap().values().stream().collect(Collectors.toMap(
                        x -> x.webSocketSession().getId(), x -> new DevWsSession()
                                .setOpen(x.webSocketSession().isOpen())
                                .setRemoteHost(Objects.requireNonNull(x.webSocketSession().getRemoteAddress()).getAddress()
                                        .getHostAddress())
                                .setRemotePort(Objects.requireNonNull(x.webSocketSession().getRemoteAddress()).getPort())
                                .setSessionId(x.webSocketSession().getId())
                                .setUri(Objects.requireNonNull(x.webSocketSession().getUri()).toString()))));
    }

    @RequestMapping("/api/session/p2pPush")
    @ResponseBody
    public String p2pPush(String sessionId, String text) {
        SessionWrapper sessionWrapper = terminalSessionManager.getSession(sessionId);
        if (sessionWrapper == null) {
            return "session is invalid";
        }
        try {
            TextMessage textMessage = new TextMessage(
                    new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                        put("text", "\u001B[?25l\n Hello World");
                        put("type", TERMINAL_PRINT);
                    }}));

            terminalSessionManager.p2pSend(sessionWrapper, new TerminalMessage() {
                @Override
                public <T> WebSocketMessage<T> webSocketMessage() {
                    return (WebSocketMessage<T>) textMessage;
                }
            });
        } catch (IOException e) {
            return "send failed," + e.getMessage();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("/api/session/rm")
    @ResponseBody
    public String rm(@RequestParam("sessionId") String sessionId) {
        SessionWrapper sessionWrapper = terminalSessionManager.getSession(sessionId);
        if (sessionWrapper == null) {
            return "session is invalid";
        }
        try {
            org.springframework.core.io.Resource resource = new ClassPathResource("rm.txt");
            List<String> strings = IOUtils.readLines(resource.getInputStream(), "utf-8");

            strings.forEach(x -> {
                TextMessage textMessage = null;
                try {
                    textMessage = new TextMessage(
                            new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                                put("text", MessageFormat.format("\u001B[?25l\n {0}", x));
                                put("type", TERMINAL_PRINT);
                            }}));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(200);
                    terminalSessionManager.p2pSend(sessionWrapper, textMessage);
                } catch (IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }
            });

        } catch (IOException e) {
            return "send failed," + e.getMessage();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping("/api/session/broadcastPush")
    @ResponseBody
    public String broadcastPush(String text) {
        Map<String, SessionWrapper> sessionMap = terminalSessionManager.sessionMap();
        try {
            TextMessage textMessage = new TextMessage(
                    new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
                        put("text", text);
                        put("type", TERMINAL_PRINT);
                    }}));
            terminalSessionManager.broadCastSend(sessionMap, textMessage);
        } catch (IOException e) {
            return "send failed," + e.getMessage();
        }
        return "ok";
    }

    // TerminalSession2ProcessManager
    @Autowired
    private TerminalSession2ProcessManager terminalSession2ProcessManager;

    @RequestMapping("/api/process/terminalSession2ProcessManager")
    @ResponseBody
    public String terminalSession2ProcessManager(String text) {
        terminalSession2ProcessManager.terminalSession2ProcessMappedMap().values().forEach(x -> {
            BufferedWriter write = x.processWrapper().write();
            try {
                write.write(text + "\r");
                write.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "ok";
    }

}
