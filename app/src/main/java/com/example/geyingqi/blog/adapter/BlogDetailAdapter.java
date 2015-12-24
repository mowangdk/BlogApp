package com.example.geyingqi.blog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geyingqi.blog.R;
import com.example.geyingqi.blog.model.Blog;
import com.example.geyingqi.blog.util.Constants;
import com.example.geyingqi.blog.util.FileUtil;
import com.example.geyingqi.blog.util.MyTagHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客内容适配器
 * Created by geyingqi on 12/15/15.
 */
public class BlogDetailAdapter extends BaseAdapter {

    private ViewHolder holder;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Blog> list;
    private SpannableStringBuilder htmlSpannable;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public BlogDetailAdapter(Context context){
        super();
        this.context = context;
        layoutInflater = layoutInflater.from(context);
        list = new ArrayList<Blog>();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.images)
                .showImageForEmptyUri(R.mipmap.images)
                .showImageOnFail(R.mipmap.images).cacheInMemory()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }


    public void setList(List<Blog> list) {
        this.list = list;
    }

    public void addList(List<Blog> list) {
        this.list.addAll(list);
    }

    public void clearList() {
        this.list.clear();
    }

    public List<Blog> getList() {
        return list;
    }

    public void removeItem(int position) {
        if (list.size() > 0) {
            list.remove(position);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Blog item = list.get(position);
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate( R.layout.article_detail_item, null);
            holder.content = (TextView)convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // System.out.println(item.getContent());

        if (null != item) {

            holder.content.setText(Html.fromHtml(item.getContent(), null,
                    new MyTagHandler()));
        }
        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        return 6;
    }



    private class ViewHolder {
        TextView id;
        TextView date;
        TextView title;
        TextView content;
        ImageView image;
        WebView code;
    }





}
