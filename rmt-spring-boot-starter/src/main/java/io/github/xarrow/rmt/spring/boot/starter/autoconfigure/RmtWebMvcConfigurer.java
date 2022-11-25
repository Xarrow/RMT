package io.github.xarrow.rmt.spring.boot.starter.autoconfigure;

import io.github.xarrow.rmt.commons.TerminalUtils;
import io.github.xarrow.rmt.spring.boot.starter.RmtStarterProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @Author: helix
 * @Time:2022/7/30
 * @Site: https://github.com/xarrow
 */
public class RmtWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private RmtStarterProperties rmtStarterProperties;
    @Autowired
    private RmtStaticFileService rmtStaticFileService;

    private String redirectIndex(HttpServletRequest request) {
        if (request.getRequestURI().endsWith("/")) {
            return "redirect:./index.html";
        }
        return "redirect:" + rmtStarterProperties.getWebPath() + "/index.html";
    }

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // rmt 服务
        String webPath = rmtStarterProperties.getWebPath();
        if (webPath != null) {
            // 配置静态资源路径
            registry.addResourceHandler(webPath + "/**").addResourceLocations("classpath:/rmt-support/");
            requestMappingHandlerMapping.registerMapping(RequestMappingInfo.paths(webPath).build(),
                    this,
                    RmtWebMvcConfigurer.class.getDeclaredMethod("redirectIndex", HttpServletRequest.class));
        }

        //  静态文件
        boolean enableStaticFileService = rmtStarterProperties.isEnableStaticFileService();
        if (!enableStaticFileService) {
            return;
        }
        String staticWebPath = rmtStarterProperties.getStaticWebPath();
        String staticFileLocation = rmtStarterProperties.getStaticFileLocation();

        // 检查本地文件夹是否存在
        String tmpFileLocation = TerminalUtils.transformToWindowsPathForCheck(staticFileLocation);
        File tmpLocalStaticLocation = new File(tmpFileLocation);
        if (!tmpLocalStaticLocation.exists()) {
            tmpLocalStaticLocation.mkdirs();
        }

        String realPathPattern = TerminalUtils.deduceStaticWebPattern(staticWebPath);
        String realStaticFileLocation = TerminalUtils.deduceStaticLocationPath(staticFileLocation);
        // 注册静态文件服务器
//        registry.addResourceHandler("/files/**").addResourceLocations("file:C:/Users/zhang/Code/RMT/RELEASE/");

        // TODO windows bugs
        registry.addResourceHandler(realPathPattern).addResourceLocations(realStaticFileLocation);
        // 静态文件服务器view
        String staticFileServiceViewPath = rmtStarterProperties.getStaticFileServiceViewPath();
        staticFileServiceViewPath = TerminalUtils.webPathFilter(staticFileServiceViewPath);
        requestMappingHandlerMapping.registerMapping(
                RequestMappingInfo.paths(staticFileServiceViewPath)
                        .methods(RequestMethod.GET)
                        .produces(MediaType.ALL_VALUE)
                        .build(),
                rmtStaticFileService,
                rmtStaticFileService.getClass().getMethod("staticFileView"));


    }
}

