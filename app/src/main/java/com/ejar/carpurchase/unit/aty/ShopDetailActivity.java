package com.ejar.carpurchase.unit.aty;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.custom.BannerView.BannerView;
import com.ejar.carpurchase.databinding.AtyShopDetailBinding;
import com.ejar.carpurchase.unit.bean.ShopDetailBean;
import com.ejar.carpurchase.utils.SystemBarHelper;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

public class ShopDetailActivity extends BaseBindingActivity<AtyShopDetailBinding> {

    private ShopDetailBean shopDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_shop_detail);
        SystemBarHelper.immersiveStatusBar(this, 0);
        SystemBarHelper.setHeightAndPadding(this, bindingView.toolbar);

        String shopId = getIntent().getStringExtra("id");
        String shopName = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(shopId)) {
            TU.cT("未获取到店铺id，请重试");
            return;
        }

        bindingView.tvName.setText(shopName);
        bindingView.tvTitle.setText(shopName);
        getDetail(shopId);

        bindingView.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight();
                boolean showTitle = bindingView.collapsingToolbar.getHeight() + verticalOffset <= bindingView.toolbar.getHeight() * 2;
                bindingView.tvTitle.setVisibility(showTitle ? View.VISIBLE : View.GONE);
                bindingView.ivReturn.setImageResource(showTitle ? R.mipmap.return_w : R.mipmap.return_c);
            }
        });


        bindingView.ivReturn.setOnClickListener(v -> finish());
    }


    /**
     * @param id shop id
     */
    private void getDetail(String id) {
        String url = UrlConf.ViewShopDetailUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url).tag(netTag)
                        .addParams("token", App.token)
                        .addParams("shopId", id)
                , url, ShopDetailActivity.this, "获取店铺详情中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        shopDetailBean = gson.fromJson(response, ShopDetailBean.class);
                        if (null != shopDetailBean && shopDetailBean.getCode() == 200) {
                            try {
                                ShopDetailBean.ShopBean item = shopDetailBean.getShop();
                                String province = TextUtils.isEmpty(item.getProvince()) ? "未知省" : " " + item.getProvince();
                                String city = TextUtils.isEmpty(item.getCity()) ? " 未知市" : " " + item.getCity();
                                String county = TextUtils.isEmpty(item.getCounty()) ? " 未知区域" : " " + item.getCounty();
                                String street = TextUtils.isEmpty(item.getStreet()) ? " 未知街道" : " " + item.getStreet();

                                String phone = TextUtils.isEmpty(item.getPhone()) ? "未知电话" : item.getPhone();
                                String type = TextUtils.isEmpty(item.getShopName()) ? "未知" : item.getShopName();
                                bindingView.tvType.setText(type);
                                bindingView.tvPeople.setText(phone);
                                bindingView.tvLocation.setText(province + city + county + street);

                                final BannerView banner2 = bindingView.bannerview;
                                banner2.setViewFactory(new BannerViewFactory());
                                banner2.setDataList(shopDetailBean.getImglist());
                                banner2.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }


    public class BannerViewFactory implements BannerView.ViewFactory<ShopDetailBean.ImgBean> {
        @Override
        public View create(ShopDetailBean.ImgBean item, final int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            Logger.e(UrlConf.IMGHOST + item.getImgUrl());
            Glide.with(container.getContext().getApplicationContext()).load(UrlConf.IMGHOST + item.getImgUrl())
                    .centerCrop()
                    .into(iv);
            return iv;
        }
    }
}
