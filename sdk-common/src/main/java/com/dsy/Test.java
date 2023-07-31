package com.dsy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/6/5 10:16
 * @Modified by:
 */
public class Test {

    public static void main(String[] args) {
        //测试文件


        try {
            // 设置要抢票的活动URL
            String eventUrl = "http://mv4qcoqdza22irfk.6tl8s8.work/chat/index/newChat/m/6058455127f67/code/gt64589569c29aa/token/gt64589569c29aa_094604bb_1e7f_bbd7_2bea_5f9b6a0c9973_1686137431605";

            // 发起HTTP GET请求
            URL url = new URL(eventUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 在响应中查找抢票按钮或关键字
            String buttonText = "立即抢票";
            if (response.toString().contains(buttonText)) {
                System.out.println("发现抢票按钮！开始执行抢票操作...");

                // TODO: 在此处添加执行抢票的代码

                System.out.println("抢票成功！");
            } else {
                System.out.println("未找到抢票按钮或关键字。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
