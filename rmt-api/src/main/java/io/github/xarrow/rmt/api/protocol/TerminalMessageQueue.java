package io.github.xarrow.rmt.api.protocol;

import java.util.List;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helicxs
 * @Date: 6/19/2020.
 * @Desc:
 */
public interface TerminalMessageQueue<T> {
    void putMessage(T t) throws InterruptedException;

    T pollMessage();

    default List<T> messageList() {return null;}
}
