package org.helixcs.rmt.spring.boot.starter;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.helixcs.rmt.api.annotation.EnableRMT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Configuration
@EnableConfigurationProperties(RmtStarterProperties.class)
@ConditionalOnClass(RequestMappingHandlerMapping.class)
@ConditionalOnProperty(prefix = "rmt.starter", name = "enable", havingValue = "true")
@EnableRMT
public class RmtStarterAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    @Lazy
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private RmtStarterProperties rmtStarterProperties;

    private String redirectIndex(HttpServletRequest request) {
        if (request.getRequestURI().endsWith("/")) {
            return "redirect:./index.html";
        }
        return "redirect:" + rmtStarterProperties.getWeb() + "/index.html";
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String web = rmtStarterProperties.getWeb();
        if (web != null) {
            // 配置静态资源路径
            registry.addResourceHandler(web + "/**").addResourceLocations("classpath:/rmt-support/");
            try {
                requestMappingHandlerMapping.registerMapping(RequestMappingInfo.paths(web).build(), this,
                    RmtStarterAutoConfiguration.class.getDeclaredMethod("redirectIndex", HttpServletRequest.class));
            } catch (NoSuchMethodException ignored) {
            }
        }
        //@Override
        //public void addViewControllers(ViewControllerRegistry registry) {
        //    addMappings(rmtStarterProperties.getWeb());
        //}
        //
        //@SneakyThrows
        //public void addMappings(final String webPoints) {
        //    if (webPoints.startsWith("/")){
        //        //webPoints = webPoints.substring()
        //    }
        //    RequestMappingInfo requestMappingInfo = RequestMappingInfo.paths(webPoints).methods(GET).build();
        //    Class<TmpController> indexControllerClass = TmpController.class;
        //    TmpController indexController = indexControllerClass.newInstance();
        //    Method method = indexControllerClass.getDeclaredMethod("index");
        //    method.setAccessible(true);
        //    requestMappingHandlerMapping.registerMapping(requestMappingInfo, indexController, method);
        //
        //}

    }

}
