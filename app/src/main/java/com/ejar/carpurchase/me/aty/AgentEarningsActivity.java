package com.ejar.carpurchase.me.aty;

import android.content.Context;
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
import com.ejar.carpurchase.databinding.AtyEarningsInfoBinding;
import com.ejar.carpurchase.me.bean.AgentEarningsBean;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/6/27.
 */

public class AgentEarningsActivity extends BaseBindingActivity<AtyEarningsInfoBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    private String state, type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_earnings_info);
        thisActivity = this;
        mContext = this.getApplicationContext();
        boolean isTeam = getIntent().getBooleanExtra("isTeam", false);
        if (isTeam) {
            state = "7";
            type2 = "7";
        } else {
            state = "6";
            type2 = "6";
        }
        initClick();
        initRecycleView();
        getList(false);
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvAdapter();
        bindingView.rvEarnings.setAdapter(adapter);
        bindingView.rvEarnings.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlEarnings.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        }, bindingView.rvEarnings);

    }

    int pageNo = 1;

    private void getList(boolean isLoadMore) {
        if (!isLoadMore)
            bindingView.clLoaddata.setStatus(LoadDataLayout.LOADING);
        bindingView.clLoaddata.setOnReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getList(isLoadMore);
            }
        });
        String url = UrlConf.AgentEarnings_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("size", "10")
                        .addParams("state", state)
                        .addParams("type", "1")
                        .addParams("type2", type2)
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlEarnings.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        AgentEarningsBean bean = gson.fromJson(response, AgentEarningsBean.class);
                        if (Objects.equals(bean.getCode(), 200 + "")) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = bean.getData().getPage().getTotlePage();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getData().getURevenueRecord());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlEarnings.setRefreshing(false);
                                adapter.setNewData(bean.getData().getURevenueRecord());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvEarnings);
                            }
                            if (bean.getData().getURevenueRecord().size() != 0) {
                                pageNo++;
                            } else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            bindingView.tvListSize.setText("共 " + adapter.getData().size() + " 条记录");
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlEarnings.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvAdapter extends BaseQuickAdapter<AgentEarningsBean.DataBean.URevenueRecordBean, BaseViewHolder> {
        public RvAdapter() {
            super(R.layout.item_rv_earnings);
        }

        @Override
        protected void convert(BaseViewHolder helper, AgentEarningsBean.DataBean.URevenueRecordBean item) {


            helper.setText(R.id.tv_type, "消费:" + item.getMoney2() + "")
//                    .setText(R.id.tv_commission, "提成：" + item.getMoney())
                    .setText(R.id.tv_custom_name, "用户名：" + item.getUserName())
                    .setText(R.id.tv_time, times(item.getTime() + ""))
            ;

        }


    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分54秒"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
