package io.github.xarrow.rmt.api.protocol;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Email: zhangjian12424@gmail.com.
 * @Author: helixcs
 * @Date: 6/19/2020.
 * @Desc:
 */
public abstract class AbstractTerminalCommandQueue implements TerminalCommandQueue<String> {
    private final LinkedBlockingDeque<String> messageQueue = new LinkedBlockingDeque<>();

    @Override
    public void putMessage(String s) throws InterruptedException {
        messageQueue.put(s);
    }

    @Override
    public String pollMessage() {
        return messageQueue.poll();
    }
}
