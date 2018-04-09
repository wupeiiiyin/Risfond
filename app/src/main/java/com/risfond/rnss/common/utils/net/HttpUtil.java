package com.risfond.rnss.common.utils.net;

import android.os.Environment;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Abbott on 2017/3/24.
 * 网络请求工具类（OkHttp）
 */

public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();
    private static HttpUtil mInstance;
    //声明OkHttpClient对象
    private OkHttpClient okHttpClient;
    final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    /**
     * Reqeust日志打印
     */
    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            UtilHelper.outLog("TAG", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            UtilHelper.outLog("TAG", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };
    private HttpUtil() {
        //第一步:初始化OkHttpClient对象 并对其设置一些属性
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(Environment.getExternalStorageDirectory(), 10 * 1024 * 1024))
                .retryOnConnectionFailure(true)
                .addInterceptor(mLoggingInterceptor)
                .build();
    }

    /**
     * 单一实例
     */
    public static HttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * http post请求
     * @param url
     * @param params
     * @param responseListener
     */
    public void requestService(String url, Map<String, String> params,String token, final ResponseListener responseListener) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry entry : params.entrySet()) {
                formBody.add(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .addHeader("Token",token)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseListener.onSuccess(response.body().string());
            }
        });
    }

    public void requestService2(String url, Map<String, Object> params,String token, final ResponseListener responseListener) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry entry : params.entrySet()) {
                formBody.add(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .addHeader("Token",token)
                .tag(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseListener.onSuccess(response.body().string());
            }
        });
    }

    /**
     * @param tag 请求标签
     * @Description 取消请求
     */
    public void cancelRequest(Object tag) {
        if (tag == null) {
            return;
        }
        synchronized (okHttpClient.dispatcher().getClass()) {
            for (Call call : okHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : okHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

}
