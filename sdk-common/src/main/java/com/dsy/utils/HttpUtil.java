package com.dsy.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author shengchaojie
 * @date 2019-03-04
 **/
public class HttpUtil {

    private static CloseableHttpClient closeableHttpClient;

    private final static Object syncLock = new Object();

    private static CloseableHttpClient getHttpClient(){
        if(closeableHttpClient==null){
            synchronized (syncLock){
                if(closeableHttpClient==null){
                    closeableHttpClient = createHttpClient();
                }
            }
        }
        return closeableHttpClient;
    }

    private static CloseableHttpClient createHttpClient(){
        //连接池默认设置不改了
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
    }

    public static String doGet(String url) throws IOException{
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse =getHttpClient().execute(httpGet);
        return EntityUtils.toString(httpResponse.getEntity(),"utf-8");
    }

}
