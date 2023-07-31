package com.dsy.interceptor;

import com.dsy.api.LogFilterApi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import java.io.IOException;
import java.util.List;

@Slf4j
public class LoggingInterceptor implements Interceptor {

    private static List<LogFilterApi> logFilterApi;

    public synchronized static void setLogCheckApi(List<LogFilterApi> logFilterApi) {
        if (LoggingInterceptor.logFilterApi == null) {
            LoggingInterceptor.logFilterApi = logFilterApi;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().url().getPath();
        if(logFilterApi == null){
            return chain.proceed(request);
        }
        //正向校验、校验内容为空 则全不打印
        boolean isNeedLog = logFilterApi.stream().filter(l -> l.checkStandard() && !l.isNeedLog(path)).findAny().isPresent();
        if (isNeedLog) {
            return chain.proceed(request);
        }
        isNeedLog = logFilterApi.stream().filter(l -> !l.checkStandard() && l.isNeedLog(path)).findAny().isPresent();
        //反向判断，校验内容有值则跳过不打印
        if (isNeedLog) {
            return chain.proceed(request);
        }
        long start = System.currentTimeMillis();
        log.info("发送请求 {}, 【{}】 参数: 【{}】", request.method(), request.url(), bodyToString(request));
        System.out.println("发送请求 = " + bodyToString(request));
        Response response = chain.proceed(request);

        long end = System.currentTimeMillis();
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        log.info("接收响应: {}【{}】 响应值:【{}】 耗时: {} ms",
                request.method(), response.request().url(), responseBody.string(), end - start);
        return response;
    }

    private String bodyToString(Request request) {

        try {
            Request copy = request.newBuilder().build();
            if (copy.body() == null) {
                return "";
            }
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "";
        }
    }
}