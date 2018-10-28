package com.quadbaze.headstart.configs;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Olatunji O. Longe
 * @created: 20 Oct, 2018, 5:19 PM
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        factory.setHttpClient(
            HttpClientBuilder.create()
                .disableCookieManagement()
                .useSystemProperties()
                .build()
        );
        return factory;
    }

    /**
     * Rest template using Apache Commons HttpClients Client Builder
     * Enables easy customization of cookie management configs
     * @param clientHttpRequestFactory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    /*@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/
}
