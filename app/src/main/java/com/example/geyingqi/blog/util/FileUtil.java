package com.example.geyingqi.blog.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by geyingqi on 12/15/15.
 */
public class FileUtil {

    //文件保存路径
    public static String filePath = Environment.getExternalStorageDirectory()+"/geyingqiBlog";

    public static String getFileName(String str){
        //去掉url中的符号作为文件名返回
        str = str.replaceAll("(?i)[^a-zA-Z0-9\\u4E00-\\u9FA5]","");
        System.out.println("filename = "+str);
        return str+".png";
    }


    //保存文件到SD卡中
    public static void writeSDcard(String filename , InputStream inputStream){
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath+"/"+filename);
            byte[] buffer = new byte[512];
            int count = 0;
            while((count = inputStream.read(buffer))>0){
                fileOutputStream.write(buffer,0,count); //写入缓冲区
            }
            fileOutputStream.flush();//写入文件
            fileOutputStream.close();//关闭文件输入流
            inputStream.close();
            System.out.println("save success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static boolean writeSDCard(String fileName,Bitmap bmp){

        try{
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
            }
            InputStream is = bitmap2InputStream(bmp);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath+"/"+getFileName(fileName));
            byte[] buffer = new byte[1024];
            int count = 0;
            while((count = is.read(buffer))>0){
                fileOutputStream.write(buffer,0,count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static InputStream bitmap2InputStream(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }


    public static String getFileContent(Context context, String file){
        String content = "";
        try {
            InputStream is = context.getResources().getAssets().open(file);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int i = is.read(buffer,0,buffer.length);
            while(i>0){
                bs.write(buffer,0, i);
                i = is.read(buffer,0,buffer.length);
            }

            content = new String(bs.toByteArray(), Charset.forName("utf-8"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;

    }

}
