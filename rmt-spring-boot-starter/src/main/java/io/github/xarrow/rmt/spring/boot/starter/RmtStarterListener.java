package io.github.xarrow.rmt.spring.boot.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class RmtStarterListener implements ApplicationListener<WebServerInitializedEvent>,
    SpringApplicationRunListener {

    private SpringApplication springApplication;
    private String[] args;
    private WebServer webServer;

    public RmtStarterListener(SpringApplication springApplication, String[] args) {
        this.springApplication = springApplication;
        this.args = args;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.webServer = event.getWebServer();

    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

        //log.info("rmt server starting at http://127.0.0.1:%d/%s", port, rmtStarterProperties.getWeb());
        log.info("RMT Server Running.");
        //System.out.println("RMT Server running.");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
