package io.github.xarrow.rmt.spring.boot.starter.configuration;

import io.github.xarrow.rmt.spring.boot.starter.RmtStarterProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

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

    private String redirectIndex(HttpServletRequest request) {
        if (request.getRequestURI().endsWith("/")) {
            return "redirect:./index.html";
        }
        return "redirect:" + rmtStarterProperties.getWebPath() + "/index.html";
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String web = rmtStarterProperties.getWebPath();
        if (web != null) {
            // 配置静态资源路径
            registry.addResourceHandler(web + "/**").addResourceLocations("classpath:/rmt-support/");
            try {
                requestMappingHandlerMapping.registerMapping(RequestMappingInfo.paths(web).build(), this,
                        RmtWebMvcConfigurer.class.getDeclaredMethod("redirectIndex", HttpServletRequest.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
