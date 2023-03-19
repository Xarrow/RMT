package io.github.xarrow.rmt.api.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.socket.TextMessage;

import java.io.Serializable;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */
@Data
@Accessors(chain = true)
public abstract class AbstractTerminalStructure implements TerminalMessage, Serializable {
    // commons
    protected MessageType type;

    public enum MessageType {
        TERMINAL_READY,
        TERMINAL_INIT,
        TERMINAL_RESIZE,
        TERMINAL_COMMAND,
        TERMINAL_CLOSE,
        TERMINAL_PRINT,
        TERMINAL_HEARTBEAT;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    public static abstract class AbstractRS extends AbstractTerminalStructure implements Serializable {
        protected String text;
    }

    public static abstract class AbstractRQ extends AbstractTerminalStructure implements Serializable {
        protected abstract AbstractRQ toTerminalMessage(final TextMessage textMessage)
                throws Exception;
    }
}
