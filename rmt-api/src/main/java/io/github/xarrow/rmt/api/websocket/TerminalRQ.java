package io.github.xarrow.rmt.api.websocket;

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
public class TerminalRQ extends AbstractTerminalStructure implements Serializable {
    // text
    protected String text;
    //type
    protected MessageType type;
    //command
    private String command;
    // resize
    // compatible
    private int columns;
    private int cols;
    private int rows;

    public TerminalRQ toTerminalRQ(TextMessage textMessage) throws Exception {
        this.textMessage = textMessage;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(textMessage.getPayload(), TerminalRQ.class);
    }
}
