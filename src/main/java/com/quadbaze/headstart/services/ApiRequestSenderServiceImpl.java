package com.quadbaze.headstart.services;

import com.quadbaze.headstart.utils.xml.XmlHandler;
import com.quadbaze.headstart.utils.json.GsonUtil;
import com.quadbaze.headstart.utils.json.Payload;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

/**
 * Created by Olatunji O. Longe on 7/4/2017.
 */

@Service
public class ApiRequestSenderServiceImpl implements ApiRequestSenderService {

    private RestTemplate restTemplate;

    @Autowired
    public ApiRequestSenderServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * Handles Sending of the http request and converts the response to json
     * @param endpointURL
     * @param method
     * @param requestPayload
     * @param mediaType
     * @return json response from remote API
     * @throws JSONException
     */
    @Override
    public JSONObject sendRequest(String endpointURL, HttpMethod method, Payload requestPayload, MediaType mediaType) throws JSONException {
        ResponseEntity<String> response = send(endpointURL, method, requestPayload, mediaType);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            return XmlHandler.toJson(response.getBody());
        }
        return new JSONObject();
    }

    /**
     * Sends the http request to the API endpoint
     * @param endpointURL
     * @param method
     * @param requestPayload
     * @param mediaType
     * @return XML response from remote API
     */
    private ResponseEntity<String> send(String endpointURL, HttpMethod method, Payload requestPayload, MediaType mediaType){

        ResponseEntity<String> response;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));

        if(method.equals(HttpMethod.GET) || method.equals(HttpMethod.DELETE)){
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpointURL);
            for(String key : requestPayload.keySet()){
                builder.queryParam(key, String.valueOf(requestPayload.get(key)));
            }
            HttpEntity<?> entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(builder.toUriString(), method, entity, String.class);
        } else if(Arrays.asList(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH).contains(method)){
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            if(requestPayload != null && mediaType != null){
                for(String key : requestPayload.keySet()){
                    map.add(key, String.valueOf(requestPayload.get(key)));
                }
            }
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
            response = restTemplate.exchange(endpointURL, method, requestEntity, String.class);
        } else {
            //ToDo modify this default assumption
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(GsonUtil.toString(requestPayload), headers);
            response = restTemplate.exchange(endpointURL, method, requestEntity, String.class, "");
        }
        return response;
    }

}
