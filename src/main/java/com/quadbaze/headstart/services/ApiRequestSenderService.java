package com.quadbaze.headstart.services;

import com.quadbaze.headstart.utils.json.Payload;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * Created by Olatunji O. Longe on 7/25/17.
 */
public interface ApiRequestSenderService {

    JSONObject sendRequest(String endpointURL, HttpMethod method, Payload requestPayload, MediaType mediaType) throws JSONException;
}
