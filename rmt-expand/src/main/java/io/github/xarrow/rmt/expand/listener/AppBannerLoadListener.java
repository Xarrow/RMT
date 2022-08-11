package io.github.xarrow.rmt.expand.listener;

import io.github.xarrow.rmt.api.listener.TerminalProcessListener;

/**
 * @Author: helix
 * @Time:2022/8/11
 * @Site: http://iliangqunru.bitcron.com/
 */
public class AppBannerLoadListener implements TerminalProcessListener {
    @Override
    public String listenerName() {
        return AppBannerLoadListener.class.getName();
    }

}
