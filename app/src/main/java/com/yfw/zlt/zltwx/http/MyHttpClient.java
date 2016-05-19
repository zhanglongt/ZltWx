package com.yfw.zlt.zltwx.http;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/4/22.
 */
public class MyHttpClient {
    public static String METHOD_GET = "GET";
    public static String METHOD_POST = "POST";
    public static String METHOD_PUT = "PUT";
    public static String METHOD_DELETE = "DELETE";

    private static final MyHttpClient mClient = new MyHttpClient();
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    static {

        mOkHttpClient.newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS);
    }

    public static MyHttpClient getmClient() {
        return mClient;
    }

    /**
     * 同步模式
     *
     * @param request
     * @return
     */
    public String comString(Request request) {
        String result = null;
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param url
     * @param method
     * @param params
     * @return
     */
    public Request getRequest(String url, String method, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        Request.Builder builder = new Request.Builder();
        if (MyHttpClient.METHOD_GET.equalsIgnoreCase(method)) {//GET
            builder.url(initGetRequest(url, params)).get();

        } else if (MyHttpClient.METHOD_POST.equalsIgnoreCase(method)) {//POST
            builder.url(url).post(initRequestBody(params));

        }

        return builder.build();
    }

    /**
     * 初始化Get请求参数
     * init Get type params
     */
    private String initGetRequest(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(url);
        //has params ?
        if (params.size() > 0) {
            sb.append("?");
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            int count = 0;
            for (Map.Entry entry : entries) {
                count++;
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                if (count == params.size()) {
                    break;
                }
                sb.append("&");
            }
            url = new String(sb);
        }
        return url;
    }

    /**
     * 初始化Body | post类型请求参数
     * init Body type params
     */
    private RequestBody initRequestBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.add(key,value.toString());
        }
        return builder.build();
    }
}
