package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ganxin.library.LoadDataLayout;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.ejar.carpurchase.App;
import com.ejar.carpurchase.R;
import com.ejar.carpurchase.base.BaseBindingActivity;
import com.ejar.carpurchase.databinding.AtyConsumeInfoBinding;
import com.ejar.carpurchase.me.bean.MyMemberIncomeBean;
import com.ejar.carpurchase.me.bean.TransferRecordBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;

public class MyMemberIncomeActivity extends BaseBindingActivity<AtyConsumeInfoBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_consume_info);
        thisActivity = this;
        mContext = this.getApplicationContext();
        bindingView.tvTitle.setText("收益");
        bindingView.ivReturn.setOnClickListener(v -> finish());
        initRecycleView();
        getList(false);
    }


    private ArrayList<TransferRecordBean.DataBean.URevenueRecordBean> list;
    private RvChargeAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvChargeAdapter();
        bindingView.rvConsumeInfo.setAdapter(adapter);
        bindingView.rvConsumeInfo.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlConsumeInfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        }, bindingView.rvConsumeInfo);

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
        String url = UrlConf.MyMemberIncome_URL;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("state", 3 + "")
                        .addParams("type", 1 + "")
                        .addParams("type2", 5 + "")
                        .addParams("pageNo", pageNo + "")
                        .addParams("pageSize", "10")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlConsumeInfo.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        MyMemberIncomeBean bean = gson.fromJson(response, MyMemberIncomeBean.class);
                        if (bean.getCode() == 200) {
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
                                bindingView.srlConsumeInfo.setRefreshing(false);
                                adapter.setNewData(bean.getData().getURevenueRecord());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvConsumeInfo);
                            }
                            if (bean.getData().getURevenueRecord().size() != 0)
                                pageNo++;
                            else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            bindingView.tvTip.setText("共 " + adapter.getData().size() + " 条相关记录");
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlConsumeInfo.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvChargeAdapter extends BaseQuickAdapter<MyMemberIncomeBean.DataBean.URevenueRecordBean, BaseViewHolder> {
        public RvChargeAdapter() {
            super(R.layout.item_rv_consume_registration);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyMemberIncomeBean.DataBean.URevenueRecordBean item) {
            /**
             * id : 27
             * userId : 16
             * shopId : 123
             * money : 12
             * state : 1   1. 余额，2.红包，3业绩（下线反的），4，货款，5，红包钱
             * type : 1    1.收入2.为支出
             * type2 : 1   1,转帐.。
             * remark : 业绩转为余额
             * time : 1496906007000
             * staye2 : 1
             * topUserID : 12
             */

            /**
             * id : 1
             * userId : 1
             * shopId :
             * money : 1000
             * state : 1
             * type : 1
             * type2 : 3
             * remark : 线下充值金额
             * time : 1498567294000
             * staye2 : 3
             * topUserID :
             * userName :
             * money2 :
             * orderId :
             * userId2 :
             */

            TextView tvTip = helper.getView(R.id.tv_tip);
            String tip = "";
            if (item.getType() == 1) {
                tvTip.setText("收入 +" + item.getMoney());
            } else tvTip.setText("支出 -" + item.getMoney());


            String type = "";
            switch (item.getState()) {
                case 1:
                    type = "余额";
                    break;
                case 2:
                    type = "红包";
                    break;
                case 3:
                    type = "业绩";
                    break;
                case 4:
                    type = "货款";
                    break;
                case 5:
                    type = "红包权";
                    break;
            }

            helper.setText(R.id.tv_type, type)
                    .setText(R.id.tv_time, times(item.getTime() + ""))
                    .setText(R.id.tv_remark, "备注:" + item.getRemark() + "")
            ;

        }


    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（14660689144）输出（"2016年6月16日 17时21分"）
     *
     * @param time
     * @return
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
