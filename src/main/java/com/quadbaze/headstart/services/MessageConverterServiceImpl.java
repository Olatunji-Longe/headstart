package com.quadbaze.headstart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Olatunji O. Longe
 * @created: 16 Oct, 2018, 10:38 PM
 */
@Service
public class MessageConverterServiceImpl implements MessageConvertersService{

    RestTemplate restTemplate;

    @Autowired
    public MessageConverterServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<HttpMessageConverter<?>> getMessageConverters(){
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.addAll(restTemplate.getMessageConverters());
        return messageConverters;
    }

}
