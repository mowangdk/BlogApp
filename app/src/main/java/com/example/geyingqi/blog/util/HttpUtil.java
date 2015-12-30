package com.example.geyingqi.blog.util;

import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by geyingqi on 12/15/15.
 */
public class HttpUtil {

    public static String cookieName = "";
    public static String cookieValue = "";
    public static String hostBase = "";

    public static String httpGetHost(String url){
        String strResult = "";
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setReadTimeout(5500);
            conn.connect();
            int response = conn.getResponseCode();
            if (200 == response){
                strResult = conn.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResult;
    }


    public static String httpGet(String url){
        System.out.println("httpGet"+url);
        String strResult = "";
        try {

            URL myurl = new URL(url);
            HttpURLConnection conn= (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5500);
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                byte[] str = new byte[1024];
                while(( is.read(str))>0){
                    strResult = new String(str,"utf-8");
                }
            }
            String strResult2 = conn.getResponseMessage();
            Log.d("HttpUtl", "show the responseMessage strResult1  = "+strResult +"strResult2 = "+strResult2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResult;
    }


    public static String httpGetNoResult(String url){
        System.out.println("httpGetNo"+url);
        String strResult = "";
        try {
            URL myurl = new URL(hostBase+url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Cookie", cookieName+"="+cookieValue);
            conn.connect();
            int code = conn.getResponseCode();
            System.out.println(code);

        } catch (Exception e) {
            e.printStackTrace();
            strResult = "error";
        }

        return strResult;
    }



    public static InputStream HttpGetBmpInputStream(String url){
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            InputStream is  = conn.getInputStream();
            return is;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap HttpGetBmp(String url){
        Bitmap bitmap = null;
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            if (200 == conn.getResponseCode()){
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Integer GetCookie(String url, String number,String pw,String select,String host){
        System.out.println("getCookie");
        int result = 4;
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        try {
            URL myurl = new URL(hostBase+url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            StringBuffer params = new StringBuffer();
            params.append("number").append("=").append(number).append("&")
                    .append("passwd").append("=").append(pw).append("&")
                    .append("select").append("=").append(select);
            byte[] bytes = params.toString().getBytes();
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            if (200 == conn.getResponseCode()){
                return 2;
            } else if (302 == conn.getResponseCode()){
                String location = conn.getHeaderField("Location");
                if (location != null && location.length() > 0){
                    Map<String,List<String>> headerFields = conn.getHeaderFields();
                    List<String> cookiesHeader = headerFields.get("Set-Cookie");
                    if (cookiesHeader != null){
                        for(String cookies : cookiesHeader){
                            List<HttpCookie> list = HttpCookie.parse(cookies);
                            for(HttpCookie cookie :list){
                               cookieName = cookie.getName();
                               cookieValue = cookie.getValue();
                            }
                            System.out.println(cookieName+cookieValue);
                            return 3;
                        }
                    }
                }

            }else if(404 == conn.getResponseCode()) {
                return -1;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpGetCookie(String url){
        Log.d("HttpUtil", "httpGetCookie "+url);
        String strResult = "";
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestProperty("Cookie", cookieName+"="+cookieValue);
            conn.setDoInput(true);
            if (200 == conn.getResponseCode()){
                strResult = conn.getResponseMessage();
            } else if (302 == conn.getResponseCode()){
                strResult = "302";
            } else if (404 == conn.getResponseCode()){
                strResult = "-1";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResult;
    }

    public static String httpPostCookie(String url,String id,String data){
        Log.d("HttpUtil", "httpPostCookie");
        String result = "4";
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            StringBuilder params = new StringBuilder();
            params.append("marc_no").append("=").append(id).append("&")
                    .append("r_content").append("=").append(data);
            conn.setRequestProperty("Cookie", cookieName+"="+cookieValue);
            byte[] bytes = params.toString().getBytes();
            conn.getOutputStream().write(bytes);
            conn.connect();

            if (200 == conn.getResponseCode()){
                return "2";
            } else if (302 == conn.getResponseCode()){
                String str = conn.getHeaderField("Location");
                if (str != null && str.length() > 0){
                    Log.d("HttpUtil", "show the str = "+ str);
                    return "3";
                }
            } else if (404 == conn.getResponseCode()){
                return "-1";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int getCookie(String url){
        Log.d("HttpUtil", "getCookie url = "+ url);
        try {
            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if (200 == conn.getResponseCode()){
                Map<String, List<String>> map = conn.getHeaderFields();
                Iterator<Map.Entry<String,List<String>>> iter =  map.entrySet().iterator();
                while(iter.hasNext()){
                    Log.d("HttpUtil", "key = "+iter.next().getKey());
                    Log.d("HttpUtil", "key = "+iter.next().getValue());
                }
                return 2;
            } else if (302 == conn.getResponseCode()){
                String link = conn.getHeaderField("Location");
                if (link != null && link.length() > 0){
                    Log.d("HttpUtil", "show the link = "+link);
                    return 3;
                }
            } else if (404 == conn.getResponseCode()) {
                return -1;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }


}
