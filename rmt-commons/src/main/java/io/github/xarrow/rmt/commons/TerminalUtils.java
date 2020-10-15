package io.github.xarrow.rmt.commons;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
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
