package com.dsy;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * {
 *      "refresh_token":"25.3b1c7d9483482cdbf0038363d6d81c6a.315360000.2006148888.282335-36960774",
 *      "expires_in":2592000,
 *      "session_key":"9mzdCrcqrExqxZQAQ7jU9ehsKnozyE+6JuRdJ\/47skT340JUuhyGbsoNi5hCENsgVFUPiKS6OJ2rWvgAvfBqerOWM\/hTdA==",
 *      "access_token":"24.ae9dac10714ee8c6b38d4d3b69483f26.2592000.1693383855.282335-36960774",
 *      "scope":"public brain_all_scope brain_mt_texttrans brain_mt_texttrans_with_dict brain_mt_doctrans brain_mt_speechtrans wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test\u6743\u9650 vis-classify_flower lpq_\u5f00\u653e cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base smartapp_mapp_dev_manage iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_\u5f00\u653eScope vis-ocr_\u865a\u62df\u4eba\u7269\u52a9\u7406 idl-video_\u865a\u62df\u4eba\u7269\u52a9\u7406 smartapp_component smartapp_search_plugin avatar_video_test b2b_tp_openapi b2b_tp_openapi_online smartapp_gov_aladin_to_xcx",
 *      "session_secret":"a7e07fe7c19cdac33bbdf251aa9c808d"
 * }
 *
 */
public class App 
{
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static void main(String []args) throws IOException {
        translate();
//        getAccessToken();

    }

    /**
     * 拿到token
     */
    public static void getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_id=kj5ioDiABVnN8an7EKv1t8Xa&client_secret=7XuAtxA2fyNKsDgXbdD3yoY16x3xwakH&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public static void translate() {
        // 请求url
        String url = "https://aip.baidubce.com/file/2.0/mt/pictrans/v1?access_token=";
        String accessToken = "24.ae9dac10714ee8c6b38d4d3b69483f26.2592000.1693383855.282335-36960774";
        try {
            Map<String, String> map = new HashMap<>();
            map.put("from", "en");
            map.put("to", "zh");
            map.put("v", "3");
            File file = new File("/Users/dongshuaiyin/Downloads/2.jpeg");

            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");
            RequestBody filebody = MultipartBody.create(MEDIA_TYPE_PNG, file);
            MultipartBody.Builder multiBuilder=new MultipartBody.Builder();
            //这里是 封装上传图片参数
            multiBuilder.addFormDataPart("image", file.getName(), filebody);
            //参数以添加header方式将参数封装，否则上传参数为空
            // 设置请求体
            multiBuilder.setType(MultipartBody.FORM);
            // 封装请求参数,这里最重要
            //参数以添加header方式将参数封装，否则上传参数为空
            if (map != null && !map.isEmpty()) {
                for (String key : map.keySet()) {
                    multiBuilder.addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                            RequestBody.create(null, map.get(key)));
                }
            }
            RequestBody multiBody=multiBuilder.build();
            Request request = new Request.Builder()
                    .url(url+accessToken)
                    .addHeader("Content-Type", "multipart/form-data")
                    .method("POST", multiBody)
                    .build();
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            Response response = HTTP_CLIENT.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
