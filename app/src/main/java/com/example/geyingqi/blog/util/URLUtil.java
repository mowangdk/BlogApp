package com.example.geyingqi.blog.util;

import com.example.geyingqi.blog.MainTabActivity;

/**
 * Created by geyingqi on 12/15/15.
 */
public class URLUtil {
    // 一系列博客URL
    public static String BASE_URL = "http://api.csdn.net/";
    public static String ARTICLE_BLOGGER_COLUMN = BASE_URL+"blog/getcolumn"; //获取博主专栏
    public static String ARTICLE_LIST= BASE_URL+"blog/getarticlelist"; //获取文章列表
    public static String ARTICLE_CONTENT = BASE_URL+"blog/getarticle"; //获取文章内容
    public static String ARTICLE_CATEGORY = BASE_URL+"blog/getcategorylist"; //获取博主自定义分类


    private static String accessToken = MainTabActivity.accessToken;



    public static String getBlogListURL(int page){
        StringBuilder sb = new StringBuilder();
        sb.append(ARTICLE_LIST).append("?access_token=").append(accessToken)
                .append("&page=").append(page);

        return sb.toString();
    }


    /**
     * 返回博文评论列表链接
     *
     * @param filename
     *            文件名
     * @param pageIndex
     *            页数
     * @return
     */
    public static String getCommentListURL(String filename, String pageIndex) {
        return "http://blog.csdn.net/wwj_748/comment/list/" + filename
                + "?page=" + pageIndex;
    }




}
