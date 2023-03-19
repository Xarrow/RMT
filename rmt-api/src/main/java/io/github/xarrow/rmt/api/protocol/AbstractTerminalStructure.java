package io.github.xarrow.rmt.api.protocol;

import org.springframework.web.socket.TextMessage;

import java.io.Serializable;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/18/2020.
 * @Desc:
 */

public abstract class AbstractTerminalStructure implements TerminalMessage, Serializable {
    protected TextMessage textMessage;

    @Override
    public TextMessage textMessage() {
        return textMessage;
    }
}
