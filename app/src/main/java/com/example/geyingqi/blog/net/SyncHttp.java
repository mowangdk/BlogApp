package com.example.geyingqi.blog.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by geyingqi on 12/20/15.
 */
public class SyncHttp {


    public String httpGet(String url,String params){

        String response = null; //返回信息
        //拼接请求URL
        if (null != params && !params.equals("")){
            url += "?" + params;
        }

        int timeOutConnection = 3000;
        int timeOutRead = 5000;
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(timeOutRead);
            conn.setConnectTimeout(timeOutConnection);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            if (200 == conn.getResponseCode()) {
                InputStream is = conn.getInputStream();
                byte[] arrays = new byte[1024];
                while (is.read(arrays) != -1) {
                    response = new String(arrays, "UTF-8");
                }
            } else {
                response = "ResponseCode = " + conn.getResponseCode();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    //通过post方式发送请求
    public String httpPost(String url , List<Parameter> params){
        String response = null;
        int timeOutConnection = 3000;
        int timeOutRead = 5000;
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(timeOutConnection);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            for(Parameter parameter : params){
                conn.setRequestProperty(parameter.getName(),parameter.getValue());
            }
            conn.connect();
            if (200 == conn.getResponseCode()){
                InputStream inputStream = conn.getInputStream();
                byte[] arrays = new byte[1024];
                while(inputStream.read(arrays) != -1) {
                    response = new String(arrays, "UTF-8");
                }
            } else {
                response = "返回码:"+conn.getResponseCode();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }




}
