package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.commons.TerminalUtils;
import io.github.xarrow.rmt.spring.boot.starter.RmtStarterProperties;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class RmtStaticFileService {

    private RmtStarterProperties rmtStarterProperties;

    @Autowired
    public void setRmtStarterProperties(RmtStarterProperties rmtStarterProperties) {
        this.rmtStarterProperties = rmtStarterProperties;
    }

    public ModelAndView staticFileView() {
        ModelAndView modelAndView = new ModelAndView();
//        URL resource = Thread.currentThread().getContextClassLoader().getResource("classpath:/rmt-support/staticFile.html");
        modelAndView.setViewName("staticFileView");

        List<SimpleFilesDesc> simpleFilesDescs = assembleStaticPath();
        modelAndView.getModel().put("items", simpleFilesDescs);
        return modelAndView;
    }

    @SneakyThrows
    private List<SimpleFilesDesc> assembleStaticPath() {
        String staticFileLocation = rmtStarterProperties.getStaticFileLocation();
        if (StringUtils.isEmpty(staticFileLocation)) {
            throw new Exception("staticFileLocation is blank in RmtStaticFileService.scanStaticFiles");
        }
        if (TerminalUtils.isWindows()) {
            staticFileLocation = TerminalUtils.transformToWindowsPathForCheck(staticFileLocation);
        }
        Path path = Paths.get(staticFileLocation);
        String staticWebPath = rmtStarterProperties.getStaticWebPath();

        String finalStaticFileLocation = staticFileLocation;
        return Files.walk(path).filter(x -> !Files.isDirectory(x)).map(x -> {
            long size = 0;
            try {
                size = Files.size(x);
            } catch (IOException e) {
            }

            String filePath = x.toString();
            String fileName =
                    filePath.replace(finalStaticFileLocation, "")
                            .replace("\\", "/");
            fileName = fileName.startsWith("/") ? fileName : "/" + fileName;
            String uri = String.format("/%s%s", staticWebPath, fileName);
            return new SimpleFilesDesc().setName("")
                    .setUri(uri)
                    .setSize(String.valueOf(size))
                    .setDesc("")
                    .setDirectory(false);
        }).collect(Collectors.toList());
    }

    @Data
    @Accessors(chain = true)
    static class SimpleFilesDesc implements Serializable {
        private String name;
        private String uri;
        private String size;
        private String desc;
        private boolean isDirectory;
    }

}
