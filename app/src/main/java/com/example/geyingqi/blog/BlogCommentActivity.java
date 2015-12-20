package com.example.geyingqi.blog;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geyingqi.blog.adapter.CommentAdapter;
import com.example.geyingqi.blog.model.Comment;
import com.example.geyingqi.blog.model.Page;
import com.example.geyingqi.blog.util.Constants;
import com.example.geyingqi.blog.util.DateUtil;
import com.example.geyingqi.blog.util.HttpUtil;
import com.example.geyingqi.blog.util.JsoupUtil;
import com.example.geyingqi.blog.util.URLUtil;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by geyingqi on 12/15/15.
 */
public class BlogCommentActivity extends Activity implements IXListViewRefreshListener, IXListViewLoadMore {


    private XListView xListView;
    private CommentAdapter adapter;

    private ProgressBar progressBar;
    private ImageView reLoadImageView;
    private ImageView backBtn;
    private TextView commentTV;
    public static String commentCount = "";

    private Page page;
    private String filename;
    private int pageIndex = 1;
    private int pageSize = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        init();
        initComponent();

        xListView.setRefreshTime(DateUtil.getDate());
        //xListView.startRefresh(); // 开始刷新
    }


    private void init(){
        filename = getIntent().getExtras().getString("filename"); //获取文件名
        page = new Page();
        adapter = new CommentAdapter(this);
    }

    private void initComponent(){
        progressBar = (ProgressBar) findViewById(R.id.newsContentPro);
        reLoadImageView = (ImageView) findViewById(R.id.reLoadImage);
        reLoadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                reLoadImageView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
            }
        });

        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        commentTV = (TextView) findViewById(R.id.comment);

        xListView = (XListView) findViewById(R.id.listview);

        xListView.setAdapter(adapter);

        xListView.setPullRefreshEnable(this);

        xListView.setPullLoadEnable(this);

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }


    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.push_no,R.anim.push_right_out);

    }

    private class MainTask extends AsyncTask<String,Void,Integer>{
        @Override
        protected Integer doInBackground(String... params) {
            // 获得返回json字符串
            String temp = HttpUtil.httpGet(URLUtil.getCommentListURL(filename,
                    page.getCurrentPage()));
            if (temp == null) {
                return Constants.DEF_RESULT_CODE.ERROR;
            }
            // 获得评论列表
            List<Comment> list = JsoupUtil.getBlogCommentList(temp,
                    Integer.valueOf(page.getCurrentPage()), pageSize);
            if (list.size() == 0) {
                return Constants.DEF_RESULT_CODE.NO_DATA;
            }

            if (params[0].equals(Constants.DEF_TASK_TYPE.LOAD)) {
                adapter.addList(list);
                return Constants.DEF_RESULT_CODE.LOAD;
            } else {
                adapter.setList(list);
                return Constants.DEF_RESULT_CODE.REFRESH;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == Constants.DEF_RESULT_CODE.ERROR) {
                Toast.makeText(getApplicationContext(), "网络信号不佳",
                        Toast.LENGTH_SHORT).show();
                xListView.stopRefresh(DateUtil.getDate());
                xListView.stopLoadMore();
                reLoadImageView.setVisibility(View.VISIBLE);
            } else if (result == Constants.DEF_RESULT_CODE.NO_DATA) {
                Toast.makeText(getApplicationContext(), "无更多评论",
                        Toast.LENGTH_SHORT).show();
                xListView.stopLoadMore();
                xListView.stopRefresh(DateUtil.getDate());
                commentTV.setText("共有评论：" + commentCount);
            } else if (result == Constants.DEF_RESULT_CODE.LOAD) {
                page.addPage();
                pageIndex++;
                adapter.notifyDataSetChanged();
                xListView.stopLoadMore();
            } else if (result == Constants.DEF_RESULT_CODE.REFRESH) {
                adapter.notifyDataSetChanged();
                xListView.stopRefresh(DateUtil.getDate());
                page.setPage(2);
                commentTV.setText("共有评论：" + commentCount);
            }
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

    }




    @Override
    public void onLoadMore() {
        new MainTask().execute(Constants.DEF_TASK_TYPE.LOAD);
    }

    @Override
    public void onRefresh() {
        page.setPage(1);
        new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
    }





}
