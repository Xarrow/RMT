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

    public static String transformToWindowsPathForCheck(String windowsFilePath) {
        return windowsFilePath.replace("file:///", "")
                .replace("file:", "")
                .replace("/", "\\");
    }

    public static String deduceStaticWebPattern(String webPattern) {
        webPattern = webPattern.startsWith("/") ? webPattern : "/" + webPattern;
        webPattern = webPattern.endsWith("/") ? webPattern : webPattern + "/";
        webPattern += "**";
        return webPattern;
    }

    public static String deduceStaticLocationPath(String locationPath) {
        if (!locationPath.startsWith("file:")) {
            locationPath = "file:" + locationPath;
        }
        locationPath = locationPath.replace("\\", "/");
        if (!locationPath.endsWith("/")) {
            locationPath = locationPath + "/";
        }
        return locationPath;

    }

    public static String webPathFilter(String staticFileServiceViewPath) {
        return staticFileServiceViewPath.startsWith("/") ? staticFileServiceViewPath : "/" + staticFileServiceViewPath;
    }


}
