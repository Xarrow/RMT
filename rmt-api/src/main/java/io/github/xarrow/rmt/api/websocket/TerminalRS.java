package io.github.xarrow.rmt.api.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.socket.TextMessage;

import java.io.Serializable;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/19/2020.
 * @Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TerminalRS extends AbstractTerminalStructure implements Serializable {
    protected String text;
    // commons
    protected MessageType type;

    public TextMessage toTextMessage() throws JsonProcessingException {
        TerminalRS terminalRS = new TerminalRS();
        terminalRS.setText(text).setType(type);
        String message = new ObjectMapper().writeValueAsString(terminalRS);
        TextMessage textMessage = new TextMessage(message);
        this.textMessage = textMessage;
        return textMessage;
    }
}
