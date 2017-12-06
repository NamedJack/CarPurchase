package com.ejar.carpurchase.unit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingFragment;
import com.ejar.carpurchase.databinding.FgtUnitBinding;
import com.ejar.carpurchase.unit.aty.ShopDetailActivity;
import com.ejar.carpurchase.unit.bean.UnitBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.glide.GlideRoundTransform;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;


/**
 * Created by wxd1 on 2017/5/25.
 */

public class UnitFragment extends BaseBindingFragment<FgtUnitBinding> {

    private ArrayList<UnitBean.DataBean.ShopBean> list;
    private RvUnitAdapter adapter;

    @Override
    public int setContent() {
        return R.layout.fgt_unit;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String title = getArguments().getString("title");
        bindingView.setTitle(title);


        list = new ArrayList<>();
        adapter = new RvUnitAdapter();
        bindingView.rvUnit.setAdapter(adapter);
        bindingView.rvUnit.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                String id = adapter.getData().get(position).getId() + "";
                String name = adapter.getData().get(position).getShopName() + "";
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        bindingView.srlUnit.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getShopList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getShopList(true);
            }
        }, bindingView.rvUnit);

    }


    @Override
    protected void loadData() {
        getShopList(false);
    }

    int pageNo = 1;

    /**
     * 获取商家列表
     */
    private void getShopList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getShopList(isLoadMore);
            }
        });
        String url = UrlConf.ViewAllShopUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("size", "10")
                , url, (AppCompatActivity) getActivity(), new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        UnitBean unitBean = gson.fromJson(response, UnitBean.class);
                        if (unitBean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPageNo = unitBean.getData().getPsge().getTotlePage();
                            if (isLoadMore) {
                                if (pageNo > totalPageNo) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(unitBean.getData().getShop());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlUnit.setRefreshing(false);
                                adapter.setNewData(unitBean.getData().getShop());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvUnit);
                            }
                            if (unitBean.getData().getShop().size() != 0)
                                pageNo++;
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }


    private class RvUnitAdapter extends BaseQuickAdapter<UnitBean.DataBean.ShopBean, BaseViewHolder> {


        public RvUnitAdapter() {
            super(R.layout.item_rv_unit);
        }

        @Override
        protected void convert(BaseViewHolder helper, UnitBean.DataBean.ShopBean item) {
            String province = item.getProvince() == null ? "未知省" : " " + item.getProvince();
            String city = item.getCity() == null ? " 未知市" : " " + item.getCity();
            String county = item.getCounty() == null ? " 未知区域" : " " + item.getCounty();
            String street = item.getStreet() == null ? " 未知街道" : " " + item.getStreet();
            helper.setText(R.id.tv_title, item.getShopName())
                    .setText(R.id.tv_location, province + city + county + street)
                    .setText(R.id.tv_people, item.getPhone())
            ;

            ImageView iv = helper.getView(R.id.iv_img);

            Glide.with(mContext).load(UrlConf.IMGHOST + item.getLog())
                    .transform(new GlideRoundTransform(mContext))
                    .centerCrop()
                    .into(iv);
        }


    }
}
