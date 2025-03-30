package com.shemuel.site.utils;


import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 公众号： 加瓦点灯
 **/
@Slf4j
public class OkHttpClientInstance {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private OkHttpClient okHttpClient = new OkHttpClient();

    {
        okHttpClient = okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS).callTimeout(60,TimeUnit.SECONDS).build();
    }

    /**
     * get 请求
     * @param url       请求url地址
     * @return string
     * */
    public String doGet(String url) {
        return doGet(url, null, null);
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     * */
    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    /**
     * get 请求
     * @param url       请求url地址
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public String doGet(String url, String[] headers) {
        return doGet(url, null, headers);
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public String doGet(String url, Map<String, String> params, String[] headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.length > 0) {
            if (headers.length % 2 == 0) {
                for (int i = 0; i < headers.length; i = i + 2) {
                    builder.addHeader(headers[i], headers[i + 1]);
                }
            } else {
//                log.warn("headers's length[{}] is error.", headers.length);
            }

        }

        Request request = builder.url(sb.toString()).build();
        return execute(request,0);
    }

    /**
     * post 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    public String doPost(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();

        return execute(request, 0);
    }

    public String doPutFile(String url, String fileName, byte[] content) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), content);
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
        Request request = new Request.Builder().put(body).url(url).build();
        return execute(request, 0);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    public String doPostJson(String url, String json, Map<String, String> headMap, String[] cookie) {
        return exectePostWithCookie(url, json, JSON, headMap,cookie);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url       请求url地址
     * @param xml       请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, String xml) {
        return exectePost(url, xml, XML, null);
    }


    private String exectePost(String url, String data, MediaType contentType, Map<String, String> headMap) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        if (headMap != null) {
            Headers setHeaders = SetHeaders(headMap);
            Request request = new Request.Builder().url(url).headers(setHeaders).post(requestBody).build();
            return execute(request,0);
        }else{
            Request request = new Request.Builder().url(url).post(requestBody).build();
            return execute(request,0);
        }
    }

    private String exectePostWithCookie(String url, String data, MediaType contentType, Map<String, String> headMap, String[] cookie) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        if (headMap != null) {
            Headers setHeaders = SetHeadersAndCookie(headMap, cookie);
            Request request = new Request.Builder().url(url).headers(setHeaders).post(requestBody).build();
            return execute(request,0);
        }else{
            Request request = new Request.Builder().url(url).post(requestBody).build();
            return execute(request,0);
        }
    }

    public static Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (ObjectUtil.isNotNull(headersParams)) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

    public static Headers SetHeadersAndCookie(Map<String, String> headersParams, String[] cookie) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (ObjectUtil.isNotNull(headersParams)) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }

        if (cookie != null ){
            for (String cook : cookie) {
                headersbuilder.add("Cookie", cook.trim());

            }
        }
        headers = headersbuilder.build();
        return headers;
    }
    private String execute(Request request, Integer retry) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.error("AI-Demo http response:code:{} {},url:{}",response.code(), com.alibaba.fastjson.JSON.toJSONString(response), request.url());
            }
        } catch (Exception e) {
            log.error("AI-Demo http error", e);
            e.printStackTrace();
            if(retry<5){
                execute(request,retry+1);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}
