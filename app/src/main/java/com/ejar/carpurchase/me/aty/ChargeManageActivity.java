package com.ejar.carpurchase.me.aty;

import android.content.Context;
import android.content.Intent;
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
import com.ejar.carpurchase.databinding.AtyChargeManageBinding;
import com.ejar.carpurchase.me.bean.ChargeBean;
import com.ejar.carpurchase.utils.conf.UrlConf;
import com.ejar.carpurchase.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;

public class ChargeManageActivity extends BaseBindingActivity<AtyChargeManageBinding> {

    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_charge_manage);
        thisActivity = this;
        mContext = this.getApplicationContext();
        initClick();
        initRecycleView();
        getList(false);
    }

    private void initClick() {
        bindingView.ivReturn.setOnClickListener(v -> finish());
        bindingView.tvAddCharge.setOnClickListener(v -> {
            Bundle b = getIntent().getExtras();
            Intent intent = new Intent(thisActivity, BalanceChargeActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    private ArrayList<ChargeBean.RecordBean.RecordsBean> list;
    private RvChargeAdapter adapter;

    private void initRecycleView() {
        list = new ArrayList<>();
        adapter = new RvChargeAdapter();
        bindingView.rvCharge.setAdapter(adapter);
        bindingView.rvCharge.setLayoutManager(new LinearLayoutManager(mContext));

        bindingView.srlCharge.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        }, bindingView.rvCharge);

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
        String url = UrlConf.ChargeRegistration;
        WNetUtil.StringCallBack(OkHttpUtils.post().url(url)
                        .addParams("token", App.token)
                        .addParams("pageNo", pageNo + "")
                        .addParams("pageSize", "10")
                , url, thisActivity, new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        bindingView.srlCharge.setRefreshing(false);
                        Logger.e(response);
                        Gson gson = new Gson();
                        ChargeBean bean = gson.fromJson(response, ChargeBean.class);
                        if (bean.getCode() == 200) {
                            bindingView.clLoaddata.setStatus(LoadDataLayout.SUCCESS);
                            int totalPage = bean.getRecord().getPages();
                            if (isLoadMore) {
                                if (pageNo > totalPage) {
                                    adapter.loadMoreEnd();
                                } else {
                                    adapter.addData(bean.getRecord().getRecords());
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                bindingView.srlCharge.setRefreshing(false);
                                adapter.setNewData(bean.getRecord().getRecords());
                                // 检查是否满一屏，如果不满足关闭loadMore
                                adapter.disableLoadMoreIfNotFullPage(bindingView.rvCharge);
                            }
                            if (bean.getRecord().getRecords().size() != 0) {
                                pageNo++;
                            } else if (!isLoadMore)
                                bindingView.clLoaddata.setStatus(LoadDataLayout.EMPTY);
                            bindingView.tvTip.setText("共 " + adapter.getData().size() + " 条充值记录");
                        } else {
                            if (isLoadMore) adapter.loadMoreFail();
                            else bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        bindingView.srlCharge.setRefreshing(false);
                        bindingView.clLoaddata.setStatus(LoadDataLayout.ERROR);
                    }
                });
    }

    class RvChargeAdapter extends BaseQuickAdapter<ChargeBean.RecordBean.RecordsBean, BaseViewHolder> {
        public RvChargeAdapter() {
            super(R.layout.item_rv_charge);
        }

        @Override
        protected void convert(BaseViewHolder helper, ChargeBean.RecordBean.RecordsBean item) {

            /**
             * money : 999
             * phone : 18113401543
             * name : bvcvb
             * adminId : 0
             * remark : 他健健康康
             * id : 7
             * time : 1496826940000
             * state : 1 //                '订单状态（1.审核中，2通过，3失败）',
             * type : 1
             * userId : 19
             * ordernumber
             */

            helper.setVisible(R.id.ll_reason, false);
            TextView tvState = helper.getView(R.id.tv_state);
            if (item.getState() == 1) {
                tvState.setText("审核中");
                tvState.setTextColor(getResources().getColor(R.color.txt_charge_aduit));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_aduit));
            } else if (item.getState() == 2) {
                tvState.setText("充值成功");
                tvState.setTextColor(getResources().getColor(R.color.txt_charge_success));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_success));
            } else {
                tvState.setText("充值失败");
                tvState.setTextColor(getResources().getColor(R.color.txt_remark));
                tvState.setBackground(getResources().getDrawable(R.drawable.charge_fail));
                helper.setText(R.id.tv_reson, "失败原因：" + item.getRemark2());
                helper.setVisible(R.id.ll_reason, true);
            }

            helper.setText(R.id.tv_order_num, "订单号:" + item.getOrdernumber() + "")
                    .setText(R.id.tv_charge_money, item.getMoney() + "")
                    .setText(R.id.tv_charge_type, "线下充值")
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
