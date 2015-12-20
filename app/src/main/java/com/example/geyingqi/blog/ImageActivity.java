package com.example.geyingqi.blog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.geyingqi.blog.util.FileUtil;
import com.example.geyingqi.blog.util.HttpUtil;
import com.polites.android.GestureImageView;

/**
 * Created by geyingqi on 12/17/15.
 */
public class ImageActivity extends Activity {

    private String url; //picture addr
    private GestureImageView imageView;//图片组件
    private ProgressBar progressBar;//进度条

    private ImageView backBtn; //回退按钮
    private ImageView downloadBtn; // 下载按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url", "");
        Log.d("ImageActivity","image url -->"+url);

        imageView = (GestureImageView) findViewById(R.id.image);
        progressBar = (ProgressBar)findViewById(R.id.loading);

        backBtn = (ImageView) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        downloadBtn = (ImageView) findViewById(R.id.download);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setDrawingCacheEnabled(true);
                if (FileUtil.writeSDCard(url, imageView.getDrawingCache())){
                    Toast.makeText(getApplicationContext(),"保存成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                imageView.setDrawingCacheEnabled(false);
            }
        });
        new MainTask().execute(url);
    }

    private class MainTask extends AsyncTask<String,Void,Bitmap>{


        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap temp = HttpUtil.HttpGetBmp(params[0]);
            return temp;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null){
                Toast.makeText(ImageActivity.this,"网络信号不佳",Toast.LENGTH_LONG).show();
            } else {
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
            }
            super.onPostExecute(bitmap);
        }

    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.push_no,R.anim.push_right_out);
    }

}
