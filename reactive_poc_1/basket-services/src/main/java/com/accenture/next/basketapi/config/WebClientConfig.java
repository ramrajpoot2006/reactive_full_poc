package com.accenture.next.basketapi.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean("clientHttpConnector")
  public ReactorClientHttpConnector clientHttpConnector(
      @Value("${webclient.timeout.connect-millis}") int connectTimeout,
      @Value("${webclient.timeout.read-millis}") int readTimeout) {
    return new ReactorClientHttpConnector(
        HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .doOnConnected(connection -> connection
                .addHandler(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))));
  }

  @Bean("orderservice")
  @DependsOn("clientHttpConnector")
  public WebClient cncDPEWebClient(ReactorClientHttpConnector clientHttpConnector,
      @Value("${orderservice.host}") String host) {
    return WebClient.builder().baseUrl(host).clientConnector(clientHttpConnector).build();
  }
}
