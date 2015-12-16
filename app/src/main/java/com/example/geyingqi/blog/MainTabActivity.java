package com.example.geyingqi.blog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.geyingqi.blog.adapter.TabAdapter;
import com.example.geyingqi.blog.view.CircleImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.update.UmengUpdateAgent;
import com.viewpagerindicator.PageIndicator;

public class MainTabActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private CircleImageView mHeadIcon;
    private ImageButton mMoreButton;
    private Context mContext = null;
    //sdk controller
    private UMSocialService mController = null;
    //要分享的文字内容
    private String mShareContent = "";
    //要分享的图片
    private UMImage mUMImgBitmap = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);

        //检测应用更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setDeltaUpdate(true);
        UmengUpdateAgent.update(this);

        FragmentPagerAdapter adapter = new TabAdapter(getSupportFragmentManager());


        //视图切换器
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(adapter);


        //页面指示器
        PageIndicator indicator = (PageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        initSlidingMenu(savedInstanceState);

        mHeadIcon = (CircleImageView) findViewById(R.id.head_icon);
        mMoreButton = (ImageButton) findViewById(R.id.personCenter);
        mHeadIcon.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);

        initConfig();
    }


    private void initSlidingMenu(Bundle savedInstanceState){
        //设置右侧滑动菜单
        setBehindContentView(R.layout.menu_frame_right);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame_two, new PersonCenterFragment()).commit();
        //实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);
        sm.setBackgroundResource(R.drawable.biz_news_local_weather_bg_big);
    }

    private void initConfig(){
        mContext = this;
        mController = UMServiceFactory.getUMSocialService(DESCRIPTOR);

        //要分享的文字内容
        mShareContent = "geyingqi blog 客户端";

        mController.setShareContent(mShareContent);
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        mUMImgBitmap = new UMImage(mContext, bitmap);
        //添加新浪和qq空间的sso授权支持
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//
//        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

    }


    private void openShareBoard(){
        mController.openShare(this,false);
    }

    //返回键触发
    @Override
    public void onBackPressed() {
        //弹出退出对话框
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainTabActivity.this)
                .setMessage("您确定要推出吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
        super.onBackPressed();
    }

    //客户端授权认证
    private class OauthTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            SyncHttp syncHttp = new SyncHttp();
            String temp = null;
            try{
                temp = syncHttp.httpPost(params[0], Potocol.getOauthParams(Potocol.USER_NAME,Potocol.PASSWORD));

            } catch (Exception e){
                e.printStackTrace();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("oauth---------->"+s);
            super.onPostExecute(s);
        }
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.head_icon:
                    getSlidingMenu().toggle();
                    break;
            case R.id.personCenter:
                    getSlidingMenu().toggle();
                    break;
            default:
                    break;
        }
    }
}
