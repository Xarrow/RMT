package org.helixcs.rmt.example.expand.session;

import lombok.Data;
import lombok.experimental.Accessors;
import org.helixcs.rmt.example.expand.session.DevWsSession;

import java.io.Serializable;
import java.util.Map;

@Data
@Accessors(chain = true)
public class DevApiRS implements Serializable {
    private Map<String, DevWsSession> devWsSessionMap;
}
