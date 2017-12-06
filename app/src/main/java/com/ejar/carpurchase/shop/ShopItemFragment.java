package com.ejar.carpurchase.shop;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.databinding.FgtShopItemBinding;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.shop.bean.ProductBean;
import com.ejar.carpurchase.utils.TU;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.glide.GlideRoundTransform;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/7/20.
 */

public class ShopItemFragment extends BaseBindingFragment<FgtShopItemBinding> {

    public static ShopItemFragment getInstance(@Nullable Bundle args) {
        ShopItemFragment fg = new ShopItemFragment();
        if (args != null)
            fg.setArguments(args);
        return fg;
    }

    public static ShopItemFragment getInstance(int flag) {
        ShopItemFragment fg = new ShopItemFragment();
        Bundle b = new Bundle();
        b.putInt("flag", flag);
        fg.setArguments(b);
        return fg;
    }

    private int flag = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getInt("flag");
    }

    @Override
    public int setContent() {
        return R.layout.fgt_shop_item;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
    }


    @Override
    protected void loadData() {
        super.loadData();
        getList(false);
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvAdapter adapter;

    private void initRecycleView() {
        adapter = new RvAdapter();
        bindingView.rvShopItem.setAdapter(adapter);
        bindingView.rvShopItem.addItemDecoration(new DividerGridItemDecoration(getContext()));
        bindingView.rvShopItem.setLayoutManager(new GridLayoutManager(getContext(), 2));

        bindingView.srlShopItem.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getList(true);
            }
        }, bindingView.rvShopItem);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent i = new Intent(getActivity(), GoodDetailActivity.class);
            i.putExtra("id", adapter.getData().get(position).getId() + "");
            startActivity(i);
        });

        // 检查是否满一屏，如果不满足关闭loadMore
        adapter.disableLoadMoreIfNotFullPage(bindingView.rvShopItem);

    }

    int pageNo = 1;
    int totalPage = 1;

    private void getList(boolean isLoadMore) {
        if (!isLoadMore) {
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        }
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getList(isLoadMore);
            }
        });
        if (isLoadMore) {
            if (pageNo > totalPage) {
                adapter.loadMoreEnd();
                return;
            }
        }else {
            pageNo = 1;
            totalPage = 1;
        }

        String url = UrlConf.ShopList_URL;
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("pageNo", pageNo + "")
                .addParams("pageSize", "10");
        if (flag != -2) {
            builder.addParams("catalogID", flag + "");
        }
        WNetUtil.StringCallBack(builder
                , url + flag, (AppCompatActivity) getActivity(), new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {

                        bindingView.srlShopItem.setRefreshing(false);
                        Logger.e(flag+" ----- "+pageNo +"  -----  "+response);
                        Gson gson = new Gson();
                        ProductBean bean = null;
                        try {
                            bean = gson.fromJson(response, ProductBean.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            TU.cT("返回数据有误");
                            bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                        if ((bean != null ? bean.getCode() : 0) == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            totalPage = bean.getData().getTotalPage();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getData().getProductList());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlShopItem.setRefreshing(false);
                                adapter.setNewData(bean.getData().getProductList());
                            }
                            if (bean.getData().getProductList().size() != 0)
                                pageNo++;
                            else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                        } else if ((bean != null ? bean.getCode() : 0) == 400) {
                            if (isLoadMore) adapter.loadMoreEnd();
                            bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlShopItem.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }


    private static class RvAdapter extends BaseQuickAdapter<ProductBean.DataBean.ProductListBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_shop);
        }


        @Override
        protected void convert(BaseViewHolder helper, ProductBean.DataBean.ProductListBean item) {
            TextView tvPriceEver = helper.getView(R.id.tv_price_ever);
            tvPriceEver.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            tvPriceEver.setText("￥" + item.getPrice() + "");
            helper.setText(R.id.tv_title, item.getName())
                    .setText(R.id.tv_price, "￥" + item.getNowPrice() + "")
            ;
            ImageView iv = helper.getView(R.id.iv);
            Glide.with(iv.getContext()).load(UrlConf.SHOP_IMGHOST + item.getImage())
                    .transform(new GlideRoundTransform(iv.getContext()))
                    .placeholder(R.drawable.placeholder)
                    .into(iv);
        }
    }
}
