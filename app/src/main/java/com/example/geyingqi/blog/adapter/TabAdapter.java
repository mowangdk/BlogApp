package com.example.geyingqi.blog.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.geyingqi.blog.BlogFrag;

/**
 * Created by geyingqi on 12/14/15.
 */
public class TabAdapter extends FragmentPagerAdapter {

    public static final String[] TITLE = new String[] { "首页", "Android",
            "cocos2d-x", "面试宝典", "Lua", "设计模式", "记录点滴", "网络协议", "Go语言", "建站经验" };

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("TabAdapter", "Fragment position :"+ position);
        switch (position){
            case 0:
                break;
            default:
                break;
        }

        return new BlogFrag(position);
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length].toUpperCase();
    }
}
