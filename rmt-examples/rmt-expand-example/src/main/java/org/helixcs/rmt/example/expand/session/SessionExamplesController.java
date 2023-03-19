//package org.helixcs.rmt.example.expand.session;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.xarrow.rmt.api.session.TerminalContext;
//import io.github.xarrow.rmt.api.session.TerminalContextManager;
//import org.apache.commons.io.IOUtils;
//import io.github.xarrow.rmt.api.protocol.TerminalMessage;
//import io.github.xarrow.rmt.api.session.TerminalSession2ProcessManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//
//import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;
//
///**
// * @Email: zhangjian12424@gmail.com.
// * @Author: helixcs
// * @Date: 6/17/2020.
// * @Desc:
// */
//
//@Controller
//@RequestMapping("examples")
//public class SessionExamplesController {
//    @RequestMapping("/")
//    public String ops() {
//        return "dev.html";
//    }
//
//    @Autowired
//    private TerminalContextManager terminalContextManager;
//
//    @RequestMapping("/api/session/showAllSession")
//    @ResponseBody
//    public DevApiRS showSession() {
//        return new DevApiRS().setDevWsSessionMap(
//                terminalContextManager.sessionMap().values().stream().collect(Collectors.toMap(
//                        x -> x.webSocketSession().getId(), x -> new DevWsSession()
//                                .setOpen(x.webSocketSession().isOpen())
//                                .setRemoteHost(Objects.requireNonNull(x.webSocketSession().getRemoteAddress()).getAddress()
//                                        .getHostAddress())
//                                .setRemotePort(Objects.requireNonNull(x.webSocketSession().getRemoteAddress()).getPort())
//                                .setSessionId(x.webSocketSession().getId())
//                                .setUri(Objects.requireNonNull(x.webSocketSession().getUri()).toString()))));
//    }
//
//    @RequestMapping("/api/session/p2pPush")
//    @ResponseBody
//    public String p2pPush(String sessionId, String text) {
//        TerminalContext terminalContext = terminalContextManager.getSession(sessionId);
//        if (terminalContext == null) {
//            return "session is invalid";
//        }
//        try {
//            TextMessage textMessage = new TextMessage(
//                    new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                        put("text", "\u001B[?25l\n Hello World");
//                        put("type", TERMINAL_PRINT);
//                    }}));
//
//            terminalContextManager.p2pSend(terminalContext, new TerminalMessage() {
//                @Override
//                public <T> WebSocketMessage<T> webSocketMessage() {
//                    return (WebSocketMessage<T>) textMessage;
//                }
//            });
//        } catch (IOException e) {
//            return "send failed," + e.getMessage();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return "ok";
//    }
//
//    @RequestMapping("/api/session/rm")
//    @ResponseBody
//    public String rm(@RequestParam("sessionId") String sessionId) {
//        TerminalContext terminalContext = terminalContextManager.getSession(sessionId);
//        if (terminalContext == null) {
//            return "session is invalid";
//        }
//        try {
//            org.springframework.core.io.Resource resource = new ClassPathResource("rm.txt");
//            List<String> strings = IOUtils.readLines(resource.getInputStream(), "utf-8");
//
//            strings.forEach(x -> {
//                TextMessage textMessage = null;
//                try {
//                    textMessage = new TextMessage(
//                            new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                                put("text", MessageFormat.format("\u001B[?25l\n {0}", x));
//                                put("type", TERMINAL_PRINT);
//                            }}));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Thread.sleep(200);
//                    terminalContextManager.p2pSend(terminalContext, textMessage);
//                } catch (IOException | InterruptedException exception) {
//                    exception.printStackTrace();
//                }
//            });
//
//        } catch (IOException e) {
//            return "send failed," + e.getMessage();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return "ok";
//    }
//
//    @RequestMapping("/api/session/broadcastPush")
//    @ResponseBody
//    public String broadcastPush(String text) {
//        Map<String, TerminalContext> sessionMap = terminalContextManager.sessionMap();
//        try {
//            TextMessage textMessage = new TextMessage(
//                    new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                        put("text", text);
//                        put("type", TERMINAL_PRINT);
//                    }}));
//            terminalContextManager.broadCastSend(sessionMap, textMessage);
//        } catch (IOException e) {
//            return "send failed," + e.getMessage();
//        }
//        return "ok";
//    }
//
//    // TerminalSession2ProcessManager
//    @Autowired
//    private TerminalSession2ProcessManager terminalSession2ProcessManager;
//
//    @RequestMapping("/api/process/terminalSession2ProcessManager")
//    @ResponseBody
//    public String terminalSession2ProcessManager(String text) {
//        terminalSession2ProcessManager.terminalSession2ProcessMappedMap().values().forEach(x -> {
//            BufferedWriter write = x.processWrapper().write();
//            try {
//                write.write(text + "\r");
//                write.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        return "ok";
//    }
//
//}
