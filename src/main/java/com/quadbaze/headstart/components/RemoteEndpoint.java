package com.quadbaze.headstart.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 2:04 PM
 */
@Component
public class RemoteEndpoint {

    @Value("${api.books-endpoint.search.url}")
    private String ENDPOINT_URL;

    @Value("${api.books-endpoint.key}")
    private String API_KEY;

    @Value("${api.books-endpoint.secret}")
    private String API_SECRET;

    public String getEndPointUrl() {
        return ENDPOINT_URL;
    }

    public String getApiKey() {
        return API_KEY;
    }

    public String getApiSecret() {
        return API_SECRET;
    }
}
