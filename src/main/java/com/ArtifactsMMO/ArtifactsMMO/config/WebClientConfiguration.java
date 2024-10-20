package com.ArtifactsMMO.ArtifactsMMO.config;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Value("${config.server}")
    private String serverUrl;

    @Value("${config.token}")
    private String token;

    @Value("${config.user.name}")
    private String username;

    @Bean("webClient")
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().secure(sslContextSpec ->
                                sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))))
                .baseUrl(serverUrl + "/my/" + username)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + token)
//                .filters(exchangeFilterFunctions -> {
//                    exchangeFilterFunctions.add(logRequest());
//                    exchangeFilterFunctions.add(logResponse());
//                })
                .build();
    }

    @Bean("characterWebClient")
    public WebClient characterWebClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().secure(sslContextSpec ->
                                sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))))
                .baseUrl(serverUrl + "/characters/" + username)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    @Bean("baseWebClient")
    public WebClient baseWebClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().secure(sslContextSpec ->
                                sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))))
                .baseUrl(serverUrl)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
//                .filters(exchangeFilterFunctions -> {
//                    exchangeFilterFunctions.add(logRequest());
//                    exchangeFilterFunctions.add(logResponse());
//                })
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value -> sb.append(value+ " ")));
                log.info(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            StringBuilder sb = new StringBuilder("Response: \n");
            //append clientRequest method and url
            sb.append(clientResponse.statusCode());
            log.info(sb.toString());
            return Mono.just(clientResponse);
        });
    }
}
