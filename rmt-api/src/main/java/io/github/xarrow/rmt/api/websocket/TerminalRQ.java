package io.github.xarrow.rmt.api.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.github.xarrow.rmt.api.protocol.AbstractTerminalStructure.AbstractRQ;
import io.github.xarrow.rmt.api.protocol.TerminalMessage;
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
public class TerminalRQ extends AbstractRQ implements Serializable {
    //command
    private String command;
    // resize
    // compatible
    private int columns;
    private int cols;
    private int rows;

    public TerminalRQ toTerminalRQ(final TextMessage textMessage) throws Exception {
        return toTerminalMessage(textMessage);
    }

    @Override
    protected TerminalRQ toTerminalMessage(final TextMessage textMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(textMessage.getPayload(), TerminalRQ.class);
    }
}
