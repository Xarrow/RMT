package org.helixcs.rmt.spring.boot.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class RmtStarterListener implements ApplicationListener<WebServerInitializedEvent>,
    SpringApplicationRunListener {

    private SpringApplication springApplication;
    private String[] args;

    public RmtStarterListener(SpringApplication springApplication, String[] args) {
        this.springApplication = springApplication;
        this.args = args;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        //log.info("rmt server starting at http://127.0.0.1:%d/%s", port, rmtStarterProperties.getWeb());

        System.out.println("RMT Server running.");
    }
}
