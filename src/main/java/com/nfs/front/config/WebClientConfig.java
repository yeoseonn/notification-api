package com.nfs.front.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    private final Integer MAX_CONNECTION = 100;

    @Bean
    public WebClient webClient() {

        ConnectionProvider cp = ConnectionProvider.builder("DefaultShortIdleConnection")
                                                  .maxIdleTime(Duration.ofSeconds(3))
                                                  .maxConnections(MAX_CONNECTION)
                                                  .build();

        HttpClient httpClient = HttpClient.create()
                                          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                          .responseTimeout(Duration.ofMillis(5000))
                                          .doOnConnected(conn -> {
                                              conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                                  .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                                          });

        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }
}
