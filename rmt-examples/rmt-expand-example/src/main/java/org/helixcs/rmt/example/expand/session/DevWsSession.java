package org.helixcs.rmt.example.expand.session;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DevWsSession implements Serializable {
    private String sessionId;
    private String uri;
    private boolean open;
    private String remoteHost;
    private int remotePort;

}
