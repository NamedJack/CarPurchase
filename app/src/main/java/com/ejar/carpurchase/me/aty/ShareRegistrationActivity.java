package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyShareRegistrationBinding;
import com.ejar.carpurchase.me.bean.ShareRegistrationBean;
import com.ejar.carpurchase.unit.bean.UnitBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 分享记录
 */
public class ShareRegistrationActivity extends BaseBindingActivity<AtyShareRegistrationBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_share_registration);
        thisActivity = this;
        mContext = this.getApplicationContext();

        initClick();
        initRecycleView();
        getShareList(false);
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.tvAddShare.setOnClickListener(v -> startActivity(new Intent(thisActivity, AddShareActivity.class)));
    }

    private ArrayList<UnitBean.DataBean.ShopBean> list;
    private RvShareAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvShareAdapter();
        bindingView.rvShare.setAdapter(adapter);
        bindingView.rvShare.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlShare.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                getShareList(false);
            }
        });


        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getShareList(true);
            }
        }, bindingView.rvShare);

    }

    int pageNo = 1;

    private void getShareList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getShareList(isLoadMore);
            }
        });
        String url = UrlConf.ViewShareRegistrationUrl;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("size", "10")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        bindingView.srlShare.setRefreshing(false);
                        Gson gson = new Gson();
                        ShareRegistrationBean bean = gson.fromJson(response, ShareRegistrationBean.class);
                        if (bean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = bean.getData().getPage().getTotlePage();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getData().getUser());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlShare.setRefreshing(false);
                                adapter.setNewData(bean.getData().getUser());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvShare);
                            }
                            if (bean.getData().getUser().size() != 0) pageNo++;
                            else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                        bindingView.tvListSize.setText("共 " + bean.getData().getUser().size() + " 条分享记录");
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlShare.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvShareAdapter extends BaseQuickAdapter<ShareRegistrationBean.DataBean.UserBean, BaseViewHolder> {
        public RvShareAdapter() {
            super(R.layout.item_rv_share);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShareRegistrationBean.DataBean.UserBean item) {

            helper.setText(R.id.tv_name, "账号：" + item.getAccount())
                    .setText(R.id.tv_time, "手机号：" + item.getPhone());
        }


    }
}
