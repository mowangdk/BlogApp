package com.example.geyingqi.blog.util;

import android.util.Log;

import com.example.geyingqi.blog.model.Blog;
import com.example.geyingqi.blog.model.BlogItem;
import com.example.geyingqi.blog.model.Blogger;
import com.example.geyingqi.blog.model.Comment;
import com.example.geyingqi.blog.net.Parameter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyingqi on 12/20/15.
 */
public class JsoupUtil {

    public static boolean contentFirstPage = true;//第一页
    public static boolean contentLastPage = true;//最后一页
    public static boolean multiPages = false; //多页
    private static final String BLOG_URL = "http://blog.csdn.net/"; //csdn博客地址

    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJScript.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJava.js\"></script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";



    public static void resetPages(){
        contentFirstPage = true;
        contentLastPage = true;
        multiPages = false;
    }

    //使用Jsoup解析html文档
    public static List<BlogItem> getBlogItemList(int blogType, String str){
        Log.d("JsoupUtil",str);
        List<BlogItem> list = new ArrayList<BlogItem>();
        //获取文档对象
        Document doc = null;
        try {
            doc = Jsoup.connect(str).get();
            Log.d("JsoupUtil",doc.toString());
            //获取class=article_list
            Elements blogList = doc.getElementsByClass("list_item list_view");
            for(Element blogItem : blogList){
                BlogItem item = new BlogItem();
                String title = blogItem.select("h1").text();//得到标题
               // String msg = blogItem.select("div.article_manage").text();
                String date = blogItem.select(".link_postdate").text();
                String link = blogItem.select(".link_title,a[href]").text();
                Log.d("JsoupUtil","title = "+title+"date = "+date+"link = "+link);
                item.setTitle(title);
                item.setDate(date);
                item.setType(blogType);
                item.setLink(link);

                item.setImgLink(null);
                list.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    //抓取传入url地址的博客详细内容
    public static List<Blog> getContent(String url, String str){
        return null;
    }

    //获取博文的评论列表
    public static List<Comment> getBlogCommentList(String str, int pageIndex,int pageSize){
        return null;
    }


    //半角转换为全角  全角---指一个字符占用两个标准字符位置。 半角---指一字符占用一个标准的字符位置。
    public static String ToDBC(String input){
        char[]  c = input.toCharArray();
        for(int i = 0;i < c.length;i++){
            if (c[i] == 12288){
                c[i] = (char)32;
                continue;
            }
            if (c[i]>65280 && c[i] < 65375)
                c[i] = (char) (c[i]-65248);
        }
        return new String(c);
    }

}
