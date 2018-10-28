package com.quadbaze.headstart;

import com.quadbaze.headstart.utils.BrowserUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring Boot Web Application!
 */
@SpringBootApplication
public class HeadstartApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HeadstartApplication.class, args);
        BrowserUtil.browse(context.getEnvironment().getProperty("app.startup.uri"));
    }
}
