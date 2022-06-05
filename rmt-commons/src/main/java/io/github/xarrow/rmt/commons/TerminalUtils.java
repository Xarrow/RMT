package io.github.xarrow.rmt.commons;

/**
 * @Email: zhangjian12424@gmail.com .
 * @Author: helixcs
 * @Date: 10/15/2020.
 * @Desc:
 */
public final class TerminalUtils {
    public static boolean isWindows() {
        return System.getProperties().get("os.name").toString().toLowerCase().contains("windows");
    }

    //todo
    public static boolean isLinux() {
        return !isWindows();
    }
}
