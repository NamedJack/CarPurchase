package com.ejar.carpurchase.shop;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.custom.BannerView.BannerView;
import com.ejar.carpurchase.custom.ObservableScrollView;
import com.ejar.carpurchase.databinding.AtyGoodDetailBinding;
import com.ejar.carpurchase.shop.bean.GoodDetailBean;
import com.ejar.carpurchase.utils.DensityUtil;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class GoodDetailActivity extends BaseBindingActivity<AtyGoodDetailBinding> {

    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_good_detail);
        productId = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(productId)) {
            TU.cT("未获取到商品id");
            finish();
        }
        bindingView.ivReturn.setOnClickListener((v) -> finish());
        int bannerHeight = DensityUtil.dip2px(getApplicationContext(), 250);
        bindingView.scrollView.setListener(new ObservableScrollView.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

                if (y > bannerHeight) {
                    bindingView.ivCart.setColorFilter(getResources().getColor(R.color.app_color));
                    bindingView.ivReturn.setColorFilter(getResources().getColor(R.color.app_color));
                } else {
                    bindingView.ivCart.setColorFilter(null);
                    bindingView.ivReturn.setColorFilter(null);
                }

            }

        });

        getGoodInfo();
    }

    String goodImgPath = "";
    /**
     * 获取商品info
     */
    private void getGoodInfo() {
        String url = UrlConf.GoodDetail_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().tag(netTag).url(url).addParams("id", productId)
                , url, this, "获取商品详情中", true, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        try {
                            GoodDetailBean detailBean = new Gson().fromJson(response, GoodDetailBean.class);

                            String[] urls = detailBean.getData().getPicture().split(",");
                            List<BannerItem> list = new ArrayList<>();
                            for (int i = 0; i < urls.length; i++) {
                                BannerItem item = new BannerItem();
                                item.image = UrlConf.SHOP_IMGHOST + urls[i];
                                list.add(item);
                                if (i==0){
                                    goodImgPath = item.image;
                                }
                            }

                            bindingView.tvPrice.setText(detailBean.getData().getNowPrice() + "");
                            bindingView.tvPriceEver.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                            bindingView.tvPriceEver.setText(detailBean.getData().getPrice() + "");
                            bindingView.tvProductName.setText(detailBean.getData().getName());
                            bindingView.bannerview.setViewFactory(new BannerViewFactory());
                            bindingView.bannerview.setDataList(list);
                            bindingView.bannerview.start();
                            String detailData = detailBean.getData().getProductHTML()+"";
                            detailData = detailData.replace("src=\"", "src=\"" + UrlConf.SHOP_IMGHOST);
                            Logger.e(detailData);

//                            bindingView.webview.loadData(detailData, "text/html; charset=UTF-8", null);//这种写法可以正确解码  ;
                            bindingView.webview.loadDataWithBaseURL(null,getHtmlData(detailData), "text/html",  "utf-8", null);
                            bindingView.llSpecPicker.setOnClickListener((v) -> {
                                SpecSelectFragment_rv.showDialog(GoodDetailActivity.this, productId,goodImgPath);
                            });
                            bindingView.tvAddToCart.setOnClickListener(v -> SpecSelectFragment_rv.showDialog(GoodDetailActivity.this, productId,goodImgPath));
                            bindingView.tvBuy.setOnClickListener(v -> SpecSelectFragment_rv.showDialog(GoodDetailActivity.this, productId,goodImgPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {

                    }
                });
    }

    /**
     * webView 适配图片
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public static class BannerItem {
        public String image;
        public String title;

        @Override
        public String toString() {
            return title;
        }
    }

    public static class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
        @Override
        public View create(BannerItem item, final int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());

            Glide.with(container.getContext().getApplicationContext()).load(item.image).placeholder(R.drawable.placeholder).centerCrop().into(iv);

            return iv;
        }
    }
}
