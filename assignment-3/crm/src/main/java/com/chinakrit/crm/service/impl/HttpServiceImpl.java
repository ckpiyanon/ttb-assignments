package com.chinakrit.crm.service.impl;

import com.chinakrit.crm.exception.HttpException;
import com.chinakrit.crm.service.HttpService;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpServiceImpl implements HttpService {

    private final OkHttpClient client = new OkHttpClient();

    public Response post(String url, Object body) throws HttpException {
        return post(url, body, new HashMap<>());
    }

    public Response post(String url, Object body, Map<String, String> headers) throws HttpException {
        String json = new Gson().toJson(body);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json"));
        Headers requestHeaders = Headers.of(headers);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(requestHeaders)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                throw new HttpException("request failed");
            }
            return response;
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }
}
