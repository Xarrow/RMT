//package io.github.xarrow.rmt.expand.listener;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.util.HashMap;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pty4j.PtyProcess;
//import io.github.xarrow.rmt.api.lifecycle.TerminalProcess;
//import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
//import io.github.xarrow.rmt.api.protocol.TerminalMessage;
//import io.github.xarrow.rmt.commons.TerminalUtils;
//import lombok.SneakyThrows;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import static io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.MessageType.TERMINAL_PRINT;
//
///**
// * @Email: zhangjian12424@gmail.com .
// * @Author: helixcs
// * @Date: 10/14/2020.
// * @Desc:
// */
//public class WindowsEmbeddedPythonEnvLoadListener implements TerminalProcessListener {
//    @Override
//    public String listenerName() {
//        return WindowsEmbeddedPythonEnvLoadListener.class.getSimpleName();
//    }
//
//    @SneakyThrows
//    @Override
//    public void afterInit(TerminalMessage message,
//                          WebSocketSession socketSession,
//                          PtyProcess ptyProcess,
//                          BufferedWriter stdout,
//                          BufferedReader stdin,
//                          BufferedReader stderr) {
//
//        if (TerminalUtils.isWindows()) {
//            String localPythonAbsPath = new ClassPathResource("python-3.9.0-embed-amd64").getFile().getAbsolutePath();
//            String localPythonEnv = "SET PATH=" + localPythonAbsPath + ";%PATH%;";
//            stdout.write(localPythonEnv);
//            stdout.write("\r");
//            stdout.flush();
//
//            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                put("text", "\nWelcome to the RMT Python  environment  ! Run \"python -V\" to check python version.\n");
//                put("type", TERMINAL_PRINT);
//            }})));
//
//
//            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                put("text", "\n========================================================\n");
//                put("type", TERMINAL_PRINT);
//            }})));
//        }
//
//        String demoPyPath = new ClassPathResource("demo.py").getFile().getAbsolutePath();
//
//        stdout.write(String.format("python %s", demoPyPath));
//        stdout.write("\r");
//        stdout.flush();
//    }
//
//
//    @Override
//    public void afterCommand(TerminalMessage message) {
//        TerminalProcessListener.super.afterCommand(message);
//    }
//
//    @Override
//    public void lifeCycleContext(TerminalProcess terminalProcess) {
//
//    }
//}
