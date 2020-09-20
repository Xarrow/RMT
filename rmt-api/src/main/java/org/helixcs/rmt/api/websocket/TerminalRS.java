package org.helixcs.rmt.api.websocket;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.helixcs.rmt.api.protocol.AbstractTerminalStructure.AbstractRS;
import org.springframework.web.socket.TextMessage;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/19/2020.
 * @Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TerminalRS extends AbstractRS implements Serializable {

    public TextMessage toTextMessage() throws JsonProcessingException {
        TerminalRS terminalRS = new TerminalRS();
        terminalRS.setText(text).setType(type);
        String message = new ObjectMapper().writeValueAsString(terminalRS);
        return new TextMessage(message);
    }
}
