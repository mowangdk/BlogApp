package com.example.geyingqi.blog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geyingqi.blog.R;
import com.example.geyingqi.blog.model.BlogItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyingqi on 12/14/15.
 */
public class BlogListAdapter extends BaseAdapter {

    private ViewHolder mHolder; //视图容器
    private LayoutInflater mLayoutInflater; //布局加载器
    private Context mContext; //上下文对象
    private List<BlogItem> mList; //博客列表

    private ImageLoader imageLoader = ImageLoader.getInstance(); //得到图片加载器
    private DisplayImageOptions options; //显示图像设置

    public BlogListAdapter(Context context){

        super();
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<BlogItem>();


        //图片加载器初始化
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        //使用DisplayImageOptions.Builder() 创建DisplayImageOptions
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.wwj_748) //设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.wwj_748) //设置url为空或者是错误的时候显示的图片
                .showImageOnFail(R.drawable.wwj_748)  //设置图片加载或者解码过程中发生错误显示的图片
                .cacheInMemory() //设置下载的图片是否缓存在内存中
                .cacheOnDisc() //设置下载的图片是否缓存在sd卡中
                .displayer(new RoundedBitmapDisplayer(20)) //设置成圆角图片
                .build(); //创建配置得到DisplayImageOption


    }


    public void setList(List<BlogItem> list){
        this.mList = list;
    }

    public List<BlogItem> getList(){
        return mList;
    }

    public void addList(List<BlogItem> list){
        this.mList.addAll(list);
    }

    public void clearList(){
        this.mList.clear();
    }

    public void removeItem(int position){
        if (mList.size() > 0){
            mList.remove(position);
        }
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (null == convertView){
            //装载布局文件
            convertView = mLayoutInflater.inflate(R.layout.blog_list_item,null);
            mHolder = new ViewHolder();
            mHolder.title = (TextView)convertView.findViewById(R.id.title);
            mHolder.date = (TextView)convertView.findViewById(R.id.date);
            mHolder.content = (TextView) convertView.findViewById(R.id.content);
            mHolder.img = (ImageView) convertView.findViewById(R.id.blogImg);
            convertView.setTag(mHolder); // 表示给View添加一个格外的数据，
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        BlogItem item = mList.get(position);
        if (null != item){
            //显示标题内容
            mHolder.title.setText(item.getTitle());
            mHolder.content.setText(item.getContent());
            mHolder.date.setText(item.getDate());
            if (item.getImgLink() != null) {
                mHolder.img.setVisibility(View.VISIBLE);
                // 异步加载图片
                imageLoader.displayImage(item.getImgLink(), mHolder.img, options);
            } else {
                //
                mHolder.img.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }


    private class ViewHolder{
        TextView id;
        TextView date;
        TextView title;
        ImageView img;
        TextView content;
    }

}
