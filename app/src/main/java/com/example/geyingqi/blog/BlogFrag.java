package com.example.geyingqi.blog;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.geyingqi.blog.adapter.BlogListAdapter;
import com.example.geyingqi.blog.model.Blog;
import com.example.geyingqi.blog.model.BlogItem;
import com.example.geyingqi.blog.model.Page;
import com.example.geyingqi.blog.util.Constants;
import com.example.geyingqi.blog.util.DB;
import com.example.geyingqi.blog.util.HttpUtil;
import com.example.geyingqi.blog.util.URLUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by geyingqi on 12/14/15.
 */
public class BlogFrag extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore{

    private XListView blogListView;//博客列表

    private View noBlogView; //无数据时显示

    private BlogListAdapter adapter; //列表适配器

    private boolean isLoad = false; //是否加载

    private int blog = 1;//博客类别

    private DB db; //数据库引用

    private Page page;

    String refreshDate = ""; //刷新日期

    public BlogFrag(){}
    @SuppressLint("ValidFragment")
    public BlogFrag(int blogType){
        this.blog = blogType;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initComponent();

        if (isLoad == false){
            isLoad = true;
            refreshDate = getDate();
            blogListView.setRefreshTime(refreshDate);
            //加载数据库中的数据
            List<BlogItem> list =  db.query(blog);
            adapter.setList(list);
            adapter.notifyDataSetChanged();

        } else {
            //blogListView.st; //不开始刷新
        }
        Log.d("NewsFrag", "onActivityCreate");
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("NewsFrag", "onCreateView");
        return inflater.inflate(R.layout.activity_main, null);
    }

    private void init() {
        db = new DB(getActivity());
        adapter = new BlogListAdapter(getActivity());
        page = new Page();
        page.setPageStart();
    }

    private void initComponent(){
        blogListView = (XListView) getView().findViewById(R.id.blogListView);
        blogListView.setAdapter(adapter); //设置适配器
        blogListView.setPullRefreshEnable(this); //设置上拉刷新
        blogListView.setPullLoadEnable(this);


        //设置列表项点击
        blogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取博客列表项
                BlogItem item = (BlogItem) adapter.getItem(position - 1);
                Intent i =  new Intent();
                i.setClass(getActivity(),BlogDetailActivity.class);
                i.putExtra("blogLink", item.getLink());
                startActivity(i);

                //动画过渡
                getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_no);
                Log.e("position", ""+position);
            }
        });


        noBlogView = getView().findViewById(R.id.noBlogLayout);


    }



    private class MainTask extends AsyncTask<String,Void,Integer>{





        @Override
        protected Integer doInBackground(String... params) {
            //获取网页html数据
            String temp = HttpUtil.httpGet(params[0]);
            if (temp == null){
                return Constants.DEF_RESULT_CODE.ERROR;
            }
            List<BlogItem> array = new ArrayList<BlogItem>();
            try {
                JSONObject jObject = new JSONObject(temp);
                JSONArray jsonArray = (JSONArray) jObject.get("list");

                for(int i = 0;i<jsonArray.length();i++){
                    BlogItem blogItem = new BlogItem();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    blogItem.setId(jsonObject.getInt("id"));
                    blogItem.setLink(jsonObject.getString("url"));
                    //blogItem.setType(jsonObject.getString("type"));
                    blogItem.setDate(jsonObject.getString("create_at"));
                    blogItem.setTitle(jsonObject.getString("title"));
                    blogItem.setViewTime(""+jsonObject.getInt("view_count"));
                    blogItem.setContent(jsonObject.getString("description"));
                    array.add(blogItem);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }



            if (array.size() == 0){
                return Constants.DEF_RESULT_CODE.NO_DATA;
            }


            //刷新动作
            if (params[1].equals("refresh")){
                adapter.setList(array);
                return Constants.DEF_RESULT_CODE.REFRESH;
            } else {//加载更多
                adapter.addList(array);
                return Constants.DEF_RESULT_CODE.LOAD;
            }

        }


        @Override
        protected void onPostExecute(Integer result) {
            adapter.notifyDataSetChanged();
            switch (result){
                case Constants.DEF_RESULT_CODE.ERROR: //错误
                    Toast.makeText(getActivity(),"网络信号不佳", Toast.LENGTH_LONG).show();
                    blogListView.stopRefresh(getDate());
                    blogListView.stopLoadMore();
                    break;
                case Constants.DEF_RESULT_CODE.NO_DATA: // 无数据
                    // Toast.makeText(getActivity(), "无更多加载内容", Toast.LENGTH_LONG)
                    // .show();
                    blogListView.stopLoadMore();
                    // noBlogView.setVisibility(View.VISIBLE); // 显示无博客
                    break;
                case Constants.DEF_RESULT_CODE.REFRESH: // 刷新
                    blogListView.stopRefresh(getDate());

                    db.delete(blog);
                    db.insert(adapter.getList());// 保存到数据库
                    if (adapter.getCount() == 0) {
                        noBlogView.setVisibility(View.VISIBLE); // 显示无博客
                    }
                    break;
                case Constants.DEF_RESULT_CODE.LOAD:
                    blogListView.stopLoadMore();
                    page.addPage();
                    if (adapter.getCount() == 0) {
                        noBlogView.setVisibility(View.VISIBLE); // 显示无博客
                    }
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);

        }

    }

    //加载更多的时候调用
    @Override
    public void onLoadMore() {
        System.out.println("loadmore");
        blog = blog+1;
        new MainTask()
                .execute(URLUtil.getBlogListURL(blog), "load");
    }



    @Override
    public void onRefresh() {
        System.out.println("refresh");
        page.setPageStart();
        new MainTask().execute(URLUtil.getBlogListURL(blog),
                "refresh");
    }


    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        return sdf.format(new Date());
    }
}
