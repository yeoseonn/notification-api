package com.nfs.front;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
@Slf4j
public class NotificationFrontServerApplication {

    public static void main(String args[]) {
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(NotificationFrontServerApplication.class);
        SpringApplication application = appBuilder.build();
        application.addListeners(new ApplicationShutdownListener());

        try {
            appBuilder.run(args);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static class ApplicationShutdownListener implements ApplicationListener<ContextClosedEvent> {
        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            log.info("Shutdown application");
        }
    }
}
