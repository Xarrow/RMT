package io.github.xarrow.rmt.api.session;

import io.github.xarrow.rmt.api.protocol.TagFilter;

import java.util.Map;

public interface TerminalSession2ProcessManager {
    <T extends TagFilter> Map<Object, TerminalSession2ProcessMap> terminalSession2ProcessMappedMap();

    // session 和 process 绑定
    // T =====> map
    <T extends TagFilter> void session2ProcessBind(final T t, final TerminalSession2ProcessMap terminalSession2ProcessMap);

    // 释放绑定关系
    // T ==X==> map
    <T extends TagFilter> void session2ProcessRelease(final T t);

    // 获取 绑定关系
    <T extends TagFilter> TerminalSession2ProcessMap getTerminalSession2ProcessMap(final T t);
}
