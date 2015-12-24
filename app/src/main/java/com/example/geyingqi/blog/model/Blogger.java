package com.example.geyingqi.blog.model;

/**
 * Created by geyingqi on 12/18/15.
 */
public class Blogger {
    private String userface; // 博主头像
    private String username; // 博主名称
    private String[] medals; // 获得勋章
    private String rank; // 排名
    private String view_count;//访问量
    private String point; //积分
    private String originalText; //原创文章
    private String transportText; //转载文章
    private String translationText; //翻译文章
    private String commentText; //评论数

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTransportText() {
        return transportText;
    }

    public void setTransportText(String transportText) {
        this.transportText = transportText;
    }

    public String getTranslationText() {
        return translationText;
    }

    public void setTranslationText(String translationText) {
        this.translationText = translationText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }



    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getMedals() {
        return medals;
    }

    public void setMedals(String[] medals) {
        this.medals = medals;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


}
