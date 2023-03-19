package io.github.xarrow.rmt.expand.session;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration.RegCheck;
import io.github.xarrow.rmt.api.session.TerminalContext;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import io.github.xarrow.rmt.api.session.AbstractTerminalContextManager;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class ConsulTerminalContextManager extends AbstractTerminalContextManager {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        ConsulTerminalContextManager ctsm = new ConsulTerminalContextManager();

        //  register a service
        String serviceId = "myServiceId";
        ImmutableRegistration build = ImmutableRegistration.builder()
            .id(serviceId)
            .name("myService")
            .check(RegCheck.http("https://baidu.com", 10L))
            .tags(Collections.singletonList("tag1"))
            .meta(Collections.singletonMap("version", "1.0"))
            .build();
        ctsm.client.agentClient().register(build);
        ctsm.client.agentClient().pass(serviceId);

        System.in.read();

    }

    private static final int DEFAULT_PORT = 8500;
    private static final int DEFAULT_WATCH_TIMEOUT = 60 * 1000;
    private static final String WATCH_TIMEOUT = "consul-watch-timeout";
    private String consulHost = "127.0.0.1";
    private int consulPort = DEFAULT_PORT;
    private final Consul client;
    private final KeyValueClient kvClient;
    private KVCache kvCache;

    public ConsulTerminalContextManager() {
        Consul.Builder builder = Consul.builder()
            .withHostAndPort(HostAndPort.fromParts(consulHost, consulPort)).withPing(true);
        client = builder.build();
        kvClient = client.keyValueClient();
    }

    @SneakyThrows
    @Override
    public void registerSession(TerminalContext terminalContext) {
        WebSocketSession ws = terminalContext.webSocketSession();
        InternalConsulWSValue internalConsulWSValue = new InternalConsulWSValue();
        internalConsulWSValue
            .setSessionId(ws.getId())
            .setRemoteHost(Objects.requireNonNull(ws.getRemoteAddress()).getHostString())
            .setRemotePort(
                ws.getRemoteAddress().getPort());
        //internalConsulWSValue.setWebSocketSession(ws);

        String s = new ObjectMapper().writeValueAsString(internalConsulWSValue);
        kvClient.putValue(ws.getId(), s);
        super.registerSession(terminalContext);
    }

    @SneakyThrows
    @Override
    public TerminalContext getSession(String sessionId) {
        String s = kvClient.getValueAsString(sessionId, Charsets.UTF_8).orElse(null);
        //if (s != null) {
        //    InternalConsulWSValue internalConsulWSValue = new ObjectMapper().readValue(s, InternalConsulWSValue
        //    .class);
        //    return new SessionWrapper() {
        //        @Override
        //        public WebSocketSession webSocketSession() {
        //            return internalConsulWSValue.getWebSocketSession();
        //        }
        //    };
        //}
        return super.getSession(sessionId);
    }

    @Override
    public void removeSession(String sessionId) {
        super.removeSession(sessionId);
        kvClient.deleteKey(sessionId);
    }

    @Data
    @Accessors(chain = true)
    private class InternalConsulWSValue implements Serializable {
        private String sessionId;
        private String remoteHost;
        private int remotePort;
        //private WebSocketSession webSocketSession;
    }

}
