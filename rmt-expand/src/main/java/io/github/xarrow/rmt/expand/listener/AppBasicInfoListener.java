//package io.github.xarrow.rmt.expand.listener;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.net.InetAddress;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pty4j.PtyProcess;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import io.github.xarrow.rmt.api.listener.TerminalProcessListener;
//import io.github.xarrow.rmt.api.protocol.TerminalMessage;
//import org.springframework.context.ApplicationContext;
//import org.springframework.core.env.AbstractEnvironment;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import static io.github.xarrow.rmt.api.websocket.MessageType.TERMINAL_PRINT;
//
///**
// * @Email: zhangjian12424@gmail.com .
// * @Author: helixcs
// * @Date: 6/23/2020.
// * @Desc: 启动加载 banner
// */
//@Slf4j
//public class AppBasicInfoListener implements TerminalProcessListener {
//    private static final String NEXT_SYMBOL = "\u001B[?25l\n";
//    private ApplicationContext context;
//
//    public void setSpringApplicationContext(ApplicationContext context) {
//        this.context = context;
//    }
//
//    @Override
//    public String listenerName() {
//        return this.getClass().getSimpleName();
//    }
//
//    public String app() {
//        AbstractEnvironment env = context.getBean(AbstractEnvironment.class);
//        List<String> lines = new ArrayList<>();
//        if (env.getProperty("spring.application.name") != null) {
//            lines.add("Application name: " + env.getProperty("spring.application.name"));
//        }
//        lines.add("User Home: " + env.getProperty("user.home"));
//        lines.add("Work Dir: " + env.getProperty("user.dir"));
//        lines.add("Shell: " + env.getProperty("SHELL", ""));
//        if (env.getProperty("PID") != null) {
//            lines.add("PID: " + env.getProperty("PID"));
//        }
//        lines.add("Java Version: " + System.getProperty("java.version"));
//        lines.add("OS Name: " + System.getProperty("os.name"));
//        lines.add("OS Version: " + System.getProperty("os.version"));
//        lines.add("OS Arch: " + System.getProperty("os.arch"));
//        // cpu + memory + disk
//        int mb = 1024 * 1024;
//        int gb = mb * 1024;
//        Runtime runtime = Runtime.getRuntime();
//        lines.add("CPU Cores: " + runtime.availableProcessors());
//        lines.add("Total Memory(M): " + runtime.totalMemory() / mb);
//        lines.add("Free Memory(M): " + runtime.freeMemory() / mb);
//        lines.add("Used Memory(M): " + (runtime.totalMemory() - runtime.freeMemory()) / mb);
//        lines.add("Max Memory(M): " + runtime.maxMemory() / mb);
//        File path = new File(".");
//        lines.add("Total Space(G): " + path.getTotalSpace() / gb);
//        lines.add("Free Space(G): " + path.getUsableSpace() / gb);
//        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();
//            lines.add("IP: " + inetAddress.getHostAddress());
//            lines.add("Host: " + inetAddress.getHostName());
//        } catch (Exception ignore) {
//
//        }
//        lines.add("\n");
//        return linesToString(lines);
//    }
//
//    String linesToString(Collection<String> lines) {
//        return String.join("\n", lines);
//    }
//
//    @Override
//    public void afterInit(TerminalMessage message) {
//        try {
//
//            socketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
//                put("text", app());
//                put("type", TERMINAL_PRINT);
//            }})));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
