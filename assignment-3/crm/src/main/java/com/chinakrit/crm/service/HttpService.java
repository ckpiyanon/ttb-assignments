package com.chinakrit.crm.service;

import com.chinakrit.crm.exception.HttpException;
import okhttp3.Response;

import java.util.Map;

public interface HttpService {
	Response post(String url, Object body) throws HttpException;
	Response post(String url, Object body, Map<String, String> headers) throws HttpException;
}
