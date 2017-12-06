package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyShopImgBinding;
import com.ejar.carpurchase.me.bean.ShopBannerBean;
import com.ejar.carpurchase.me.bean.UserDataBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ShopImgActivity extends BaseBindingActivity<AtyShopImgBinding> {

    private String shopImgPath;
    private String bannerThreePath;
    private String bannerTwoPath;
    private String bannerOnePath;
    private Context mContext;
    private AppCompatActivity thisActivity;
    private List<ShopBannerBean.DataBean> bannerList;
    private String logImg;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_img);
        thisActivity = this;
        mContext = this.getApplicationContext();
        bannerList = new ArrayList<>();


        Bundle b = getIntent().getExtras();
        UserDataBean.EntityshopBean shopInfoBean = (UserDataBean.EntityshopBean) b.getSerializable("shopInfo");
        shopId = shopInfoBean.getId() + "";
        if (TextUtils.isEmpty(shopId)) {
            TU.cT("未获取到店铺id，请重试");
            return;
        }
        logImg = UrlConf.HOST + shopInfoBean.getLog();
        Glide.with(mContext).load(logImg).into(bindingView.iv);
        getBanner(shopId);
        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.iv.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 100);
            backgroundAlpha(0.5f);
        });

        bindingView.ivBannerOne.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 101);
            backgroundAlpha(0.5f);
        });

        bindingView.ivBannerTwo.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 102);
            backgroundAlpha(0.5f);
        });

        bindingView.ivBannerThree.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 103);
            backgroundAlpha(0.5f);
        });

        bindingView.btnSave.setOnClickListener(v -> doSubmit());
    }


    /**
     * @param id shopId
     */
    private void getBanner(String id) {
        String url = UrlConf.QueryShopImglUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("shopId", id)
                , url, thisActivity, "获取轮播图中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        ShopBannerBean shopBannerBean = gson.fromJson(response, ShopBannerBean.class);
                        bannerList.clear();
                        bannerList.addAll(shopBannerBean.getData());
                        if (shopBannerBean.getData().size()==0){
                            TU.cT("暂无轮播图");
                            return;
                        }
                        showPic(bannerList);

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }

    private void showPic(List<ShopBannerBean.DataBean> bannerList) {
        for (int i = 0; i < bannerList.size(); i++) {
            if (i == 0) {
                Glide.with(mContext).load(UrlConf.IMGHOST + bannerList.get(0).getImgUrl()).into(bindingView.ivBannerOne);
            } else if (i == 1) {
                Glide.with(mContext).load(UrlConf.IMGHOST + bannerList.get(1).getImgUrl()).into(bindingView.ivBannerTwo);
            } else if (i == 2) {
                Glide.with(mContext).load(UrlConf.IMGHOST + bannerList.get(2).getImgUrl()).into(bindingView.ivBannerThree);
            }
        }
    }


    private void updateLog() {
        String url = UrlConf.UpdateLogUrl;

        if (TextUtils.isEmpty(shopImgPath)) return;
        WNetUtil.StringCallBack(OkHttpUtils.post().tag(netTag).url(url).addParams("token", App.token)
                        .addParams("shopId", shopId)
                        .addFile("log", SystemClock.currentThreadTimeMillis() + ".jpg", new File(shopImgPath))
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }

    private void doSubmit() {
        if (bannerList.size() != 3) {
            if (TextUtils.isEmpty(bannerOnePath) || TextUtils.isEmpty(bannerTwoPath) || TextUtils.isEmpty(bannerThreePath)) {
                TU.cT("请一次性添加三张图片！");
                return;
            }
        }
        String url = "";
        if (bannerList.size() != 3) url = UrlConf.AddShopImglUrl;
        else url = UrlConf.UpdateShopImglUrl;

        PostFormBuilder builder = OkHttpUtils.post().url(url).tag(netTag).addParams("token", App.token)
                .addParams("shopId", shopId);
        if (!TextUtils.isEmpty(bannerOnePath)) {
            if (bannerList.size() != 3)
                builder.addFile("1", SystemClock.currentThreadTimeMillis() + "1.jpg", new File(bannerOnePath));
            else if (TextUtils.isEmpty(bannerList.get(0).getImgUrl())) {
                builder.addFile("1", SystemClock.currentThreadTimeMillis() + "1.jpg", new File(bannerOnePath));
            } else
                builder.addFile(bannerList.get(0).getId() + "", SystemClock.currentThreadTimeMillis() + "1.jpg", new File(bannerOnePath));
        }
        if (!TextUtils.isEmpty(bannerTwoPath)) {
            if (bannerList.size() != 3)
                builder.addFile("2", SystemClock.currentThreadTimeMillis() + "2.jpg", new File(bannerTwoPath));
            else if (TextUtils.isEmpty(bannerList.get(1).getImgUrl())) {
                builder.addFile("2", SystemClock.currentThreadTimeMillis() + "2.jpg", new File(bannerTwoPath));
            } else
                builder.addFile(bannerList.get(1).getId() + "", SystemClock.currentThreadTimeMillis() + "2.jpg", new File(bannerTwoPath));
        }
        if (!TextUtils.isEmpty(bannerThreePath)) {
            if (bannerList.size() != 3)
                builder.addFile("3", SystemClock.currentThreadTimeMillis() + "3.jpg", new File(bannerThreePath));
            else if (TextUtils.isEmpty(bannerList.get(2).getImgUrl())) {
                builder.addFile("3", SystemClock.currentThreadTimeMillis() + "3.jpg", new File(bannerThreePath));
            } else
                builder.addFile(bannerList.get(2).getId() + "", SystemClock.currentThreadTimeMillis() + "3.jpg", new File(bannerThreePath));
        }


        WNetUtil.StringCallBack(builder
                , url, thisActivity, "上传图片中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            TU.cT(jsonObject.optString("msg"));
                            if (jsonObject.optInt("code") == 200) finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundAlpha(1f);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String imgPath = data.getStringExtra("path");
            switch (requestCode) {
                case 100:
                    shopImgPath = imgPath;
                    Glide.with(mContext)
                            .load(shopImgPath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(bindingView.iv);
                    updateLog();
                    break;
                case 101:
                    bannerOnePath = imgPath;
                    Glide.with(mContext)
                            .load(bannerOnePath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(bindingView.ivBannerOne);
                    break;
                case 102:
                    bannerTwoPath = imgPath;
                    Glide.with(mContext)
                            .load(bannerTwoPath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(bindingView.ivBannerTwo);
                    break;
                case 103:
                    bannerThreePath = imgPath;
                    Glide.with(mContext)
                            .load(bannerThreePath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(bindingView.ivBannerThree);
                    break;
            }

        }
    }
}
