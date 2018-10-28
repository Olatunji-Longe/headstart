package com.quadbaze.headstart.services;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Olatunji O. Longe
 * @created: 15 Sep, 2018, 5:30 PM
 */
public interface MessageConvertersService {
    List<HttpMessageConverter<?>> getMessageConverters();
}
